package com.dihaw.portal.liferay.webcontoc.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**		Form model used to store the role for every page in a layout.	*/
public class PagesForm implements Serializable {
    private static final long serialVersionUID = 5778881618663448037L;

    private List<Row> rows;

    public List<Row> getRows() {
	if (rows == null) {
	    rows = new ArrayList<PagesForm.Row>();
	}
	return rows;
    }

    public static class Row implements Serializable {
	private static final long serialVersionUID = 3369686163775338700L;

	private long pageId;
	private String roleName;
	private String title;
	private int level;
	private boolean leaf;

	public long getPageId() {
	    return pageId;
	}

	public void setPageId(long pageId) {
	    this.pageId = pageId;
	}

	public String getRoleName() {
	    return roleName;
	}

	public void setRoleName(String roleName) {
	    this.roleName = roleName;
	}

	public String getTitle() {
	    return title;
	}

	public void setTitle(String title) {
	    this.title = title;
	}

	public int getLevel() {
	    return level;
	}

	public void setLevel(int level) {
	    this.level = level;
	}

	public boolean isLeaf() {
	    return leaf;
	}

	public void setLeaf(boolean leaf) {
	    this.leaf = leaf;
	}
    }
}
