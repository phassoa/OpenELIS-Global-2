package spring.service.panel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.service.common.BaseObjectServiceImpl;
import us.mn.state.health.lims.common.exception.LIMSDuplicateRecordException;
import us.mn.state.health.lims.panel.dao.PanelDAO;
import us.mn.state.health.lims.panel.valueholder.Panel;

@Service
public class PanelServiceImpl extends BaseObjectServiceImpl<Panel, String> implements PanelService {

	@Autowired
	protected PanelDAO baseObjectDAO;

	PanelServiceImpl() {
		super(Panel.class);
	}

	@Override
	protected PanelDAO getBaseObjectDAO() {
		return baseObjectDAO;
	}

	@Override
	public void getData(Panel panel) {
		getBaseObjectDAO().getData(panel);

	}

	@Override
	public void deleteData(List panels) {
		getBaseObjectDAO().deleteData(panels);

	}

	@Override
	public void updateData(Panel panel) {
		getBaseObjectDAO().updateData(panel);

	}

	@Override
	public boolean insertData(Panel panel) {
		return getBaseObjectDAO().insertData(panel);
	}

	@Override
	public String getIdForPanelName(String name) {
		return getBaseObjectDAO().getIdForPanelName(name);
	}

	@Override
	public String getDescriptionForPanelId(String id) {
		return getBaseObjectDAO().getDescriptionForPanelId(id);
	}

	@Override
	public String getNameForPanelId(String panelId) {
		return getBaseObjectDAO().getNameForPanelId(panelId);
	}

	@Override
	public List<Panel> getAllActivePanels() {
		return getBaseObjectDAO().getAllActivePanels();
	}

	@Override
	public List getNextPanelRecord(String id) {
		return getBaseObjectDAO().getNextPanelRecord(id);
	}

	@Override
	public List getPreviousPanelRecord(String id) {
		return getBaseObjectDAO().getPreviousPanelRecord(id);
	}

	@Override
	public Integer getTotalPanelCount() {
		return getBaseObjectDAO().getTotalPanelCount();
	}

	@Override
	public List getActivePanels(String filter) {
		return getBaseObjectDAO().getActivePanels(filter);
	}

	@Override
	public Panel getPanelByName(String panelName) {
		return getBaseObjectDAO().getPanelByName(panelName);
	}

	@Override
	public Panel getPanelByName(Panel panel) {
		return getBaseObjectDAO().getPanelByName(panel);
	}

	@Override
	public List getPageOfPanels(int startingRecNo) {
		return getBaseObjectDAO().getPageOfPanels(startingRecNo);
	}

	@Override
	public String insert(Panel panel) {
		return (String) super.insert(panel);
	}

	@Override
	public Panel update(Panel panel) {
		if (baseObjectDAO.duplicatePanelExists(panel)) {
			throw new LIMSDuplicateRecordException("Duplicate record exists for " + panel.getPanelName());
		}
		// AIS - bugzilla 1563
		if (baseObjectDAO.duplicatePanelDescriptionExists(panel)) {
			throw new LIMSDuplicateRecordException("Duplicate record exists for panel description");
		}
		panel = super.update(panel);
		baseObjectDAO.clearIDMaps();
		return panel;
	}

	@Override
	public List<Panel> getAllPanels() {
		return baseObjectDAO.getAllPanels();
	}

	@Override
	public Panel getPanelById(String id) {
		return baseObjectDAO.getPanelById(id);
	}
}
