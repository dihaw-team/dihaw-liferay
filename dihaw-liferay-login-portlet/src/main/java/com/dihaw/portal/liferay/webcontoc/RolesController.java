package com.dihaw.portal.liferay.webcontoc;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.dihaw.portal.liferay.LoginUtils;
import com.dihaw.portal.liferay.Settings;
import com.dihaw.web.schemas.profile.sync.RoleListRequest;
import com.dihaw.web.schemas.profile.sync.RoleListResponse;
import com.dihaw.web.schemas.profile.sync.RoleType;
import com.liferay.portal.DuplicateRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.RoleLocalServiceUtil;

@Controller
@RequestMapping(value = "VIEW", params = "tabs1=roles")
public class RolesController {
    /** The logger for this instance. */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @RenderMapping(params = "!display")
    public String showActions() {
	return "roles";
    }

    @ActionMapping("syncRoles")
    public void syncRoles(ActionRequest request, ActionResponse response) throws SystemException, PortalException {
	logger.info("Requesting synchronization of roles.");

	long companyId = LoginUtils.getThemeDisplay().getCompanyId();

	RoleListResponse roleList = restTemplate.postForObject(Settings.getRemoteRoleListURL(), new RoleListRequest(),
		RoleListResponse.class);

	List<String> roleNames = extract(roleList.getRole(), on(RoleType.class).getName());

	List<Role> liferayRoles = RoleLocalServiceUtil.getRoles(companyId);
	List<String> liferayRoleNames = filter(startsWith("ROLE_"), extract(liferayRoles, on(Role.class).getName()));

	List<String> rolesToDelete = new ArrayList<String>(liferayRoleNames);
	rolesToDelete.removeAll(roleNames);
	List<String> rolesToAdd = new ArrayList<String>(roleNames);
	rolesToAdd.removeAll(liferayRoleNames);
	List<String> rolesToUpdate = new ArrayList<String>(roleNames);
	rolesToUpdate.removeAll(rolesToAdd);

	for (String roleName : rolesToDelete) {
	    logger.info("Deleting role with name '{}' from Liferay.", roleName);

	    Role role = RoleLocalServiceUtil.getRole(companyId, roleName);
	    RoleLocalServiceUtil.deleteRole(role);
	}
	for (String roleName : rolesToUpdate) {
	    logger.info("Updating role with name '{}' on Liferay.", roleName);

	    Role role = RoleLocalServiceUtil.getRole(companyId, roleName);
	    RoleType roleType = select(roleList.getRole(), having(on(RoleType.class).getName(), equalTo(roleName)))
		    .get(0);
//	    role.setTitle(Locale.ITALY, roleType.getDescription());
	    RoleLocalServiceUtil.updateRole(role, true);
	}
	for (String roleName : rolesToAdd) {
	    logger.info("Adding role with name '{}' on Liferay roles.", roleName);

	    RoleType roleType = select(roleList.getRole(), having(on(RoleType.class).getName(), equalTo(roleName)))
		    .get(0);
	    try {
		RoleLocalServiceUtil.addRole(0, companyId, roleName,
			Collections.singletonMap(Locale.ITALY, roleType.getDescription()), null,
			RoleConstants.TYPE_REGULAR);
	    } catch (DuplicateRoleException dre) {
		logger.warn("Role with name '{}' already present. Skipping.", roleName);
	    }
	}

	SessionMessages.add(request, "roles-synchronized");

	response.setRenderParameter("tabs1", "roles");
    }
}
