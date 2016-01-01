package com.dihaw.portal.liferay.webcontoc;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.sort;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.portal.liferay.LoginUtils;
import com.dihaw.portal.liferay.webcontoc.form.PagesForm;
import com.dihaw.portal.liferay.webcontoc.form.PagesForm.Row;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

/**		Controller used to assign the role to pages.		*/
@Controller
@RequestMapping(value = "VIEW", params = "tabs1=pages")
public class PagesController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String ROLE_NAME_COLUMN_NAME = "role-name";

    public static final String PAGE_FORM = "model";
    public static final String ROLE_LIST_MODEL = "roles";

    @ModelAttribute(PAGE_FORM)
    public PagesForm getFormModel() throws PortalException, SystemException {
	PagesForm form = new PagesForm();

	// Populate the form with data from the database.
	long companyId = LoginUtils.getThemeDisplay().getCompanyId();
	long groupId = LoginUtils.getThemeDisplay().getDoAsGroupId();
	List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, true, 0l);
	fillRows(companyId, form.getRows(), layouts, 1);

	return form;
    }

    private void fillRows(long companyId, List<Row> rows, List<Layout> layouts, int level) throws PortalException,
	    SystemException {
	logger.info("Filling rows for layouts {}", layouts);

	for (Layout layout : layouts) {
	    Row row = new Row();
	    row.setPageId(layout.getPlid());
	    row.setRoleName(ExpandoValueLocalServiceUtil.getData(companyId, Layout.class.getName(),
		    ExpandoTableConstants.DEFAULT_TABLE_NAME, ROLE_NAME_COLUMN_NAME, layout.getPrimaryKey(),
		    (String) null));
	    row.setLevel(level);
	    row.setTitle(layout.getName(LocaleContextHolder.getLocale()));
	    row.setLeaf(!layout.hasChildren());
	    rows.add(row);

	    // Do recursion
	    fillRows(companyId, rows, layout.getChildren(), level + 1);
	}
    }

    @ModelAttribute(ROLE_LIST_MODEL)
    public List<Role> getRoles() throws PortalException, SystemException {
	long companyId = LoginUtils.getThemeDisplay().getCompanyId();
	String languageId = LoginUtils.getThemeDisplay().getLanguageId();

	List<Role> roles = sort(RoleLocalServiceUtil.getRoles(companyId), on(Role.class).getTitle(languageId));
	return roles;
    }

    @RenderMapping
    public String showPagesForm(Model model) throws PortalException, SystemException {
	return "pages";
    }

    @ActionMapping("updatePages")
    public void doUpdatePages(@ModelAttribute(PAGE_FORM) PagesForm form, ActionRequest request, ActionResponse response)
	    throws PortalException, SystemException {
	logger.info("Performing the page updates.");

	List<Row> rows = select(form.getRows(), having(on(Row.class).isLeaf(), equalTo(Boolean.TRUE)));
	long companyId = LoginUtils.getThemeDisplay().getCompanyId();
	for (Row row : rows) {
	    if (!"--".equals(row.getTitle())) {
		doUpdateRow(companyId, row);
	    }
	}

	SessionMessages.add(request, "pages-update-completed");
	response.setRenderParameter("tabs1", "pages");
    }

    private void doUpdateRow(long companyId, Row row) throws PortalException, SystemException {
	Layout layout = LayoutLocalServiceUtil.getLayout(row.getPageId());

	String previousRoleName = ExpandoValueLocalServiceUtil.getData(companyId, Layout.class.getName(),
		ExpandoTableConstants.DEFAULT_TABLE_NAME, ROLE_NAME_COLUMN_NAME, layout.getPlid(), (String) "");

	if (!StringUtils.equals(previousRoleName, row.getRoleName())) {
	    logger.info("Performing update of role for page '{}'.", row.getTitle());

	    unsetRoleFromPage(companyId, layout, previousRoleName);
	    setRoleToPage(companyId, layout, row.getRoleName());

	    ExpandoValueLocalServiceUtil.addValue(companyId, Layout.class.getName(),
		    ExpandoTableConstants.DEFAULT_TABLE_NAME, ROLE_NAME_COLUMN_NAME, layout.getPlid(),
		    row.getRoleName());
	} else {
	    logger.debug("Skipping update of role for page '{}' because wasn't modified.", row.getTitle());
	}
    }

    private void setRoleToPage(long companyId, Layout layout, String roleName) throws PortalException, SystemException {
	if (!isEmpty(roleName)) {
	    Role role = RoleLocalServiceUtil.getRole(companyId, roleName);

	    List<Layout> layouts = new ArrayList<Layout>();
	    layouts.add(layout);
	    layouts.addAll(layout.getAncestors());

		Role orgMemberRole = RoleLocalServiceUtil.getRole(companyId, "Organization Member");
	    
	    for (Layout page : layouts) {
		ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(page.getPlid()), role.getRoleId(),
			new String[] { ActionKeys.VIEW });
		
		logger.debug("Removing role Organization Member to the page");
		ResourcePermissionLocalServiceUtil.removeResourcePermission(companyId, Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(page.getPlid()), orgMemberRole.getRoleId(),
				ActionKeys.VIEW);
	    }
	}
    }

    private void unsetRoleFromPage(long companyId, Layout layout, String roleName) throws PortalException,
	    SystemException {
	if (!isEmpty(roleName)) {
	    try {
			Role role = RoleLocalServiceUtil.getRole(companyId, roleName);
			
		    List<Layout> layouts = new ArrayList<Layout>();
		    layouts.add(layout);
		    layouts.addAll(layout.getAncestors());
	
		    for (Layout page : layouts) {
			ResourcePermissionLocalServiceUtil.removeResourcePermission(companyId, Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, Long.toString(page.getPlid()), role.getRoleId(),
				ActionKeys.VIEW);						
		    }
	    } catch (NoSuchRoleException nsre) {
	    	
	    	logger.warn("No role found for roleName {}", roleName);
	    }
		  
	}
    }
}
