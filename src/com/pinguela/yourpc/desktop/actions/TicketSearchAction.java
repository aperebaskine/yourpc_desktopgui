package com.pinguela.yourpc.desktop.actions;

import javax.swing.table.TableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.YPCException;
import com.pinguela.yourpc.desktop.constants.TicketTableConstants;
import com.pinguela.yourpc.desktop.model.ActionPaneListTableModel;
import com.pinguela.yourpc.desktop.view.SearchView;
import com.pinguela.yourpc.desktop.view.TicketSearchView;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;

public class TicketSearchAction extends SearchAction<Ticket> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3638461893818380168L;
	
	private static Logger logger = LogManager.getLogger(TicketSearchAction.class);
	
	private TicketService ticketService;

	public TicketSearchAction(SearchView<Ticket> view) {
		super(view);
		this.ticketService = new TicketServiceImpl();
	}

	@Override
	protected TableModel fetchData() {

		TicketSearchView view = (TicketSearchView) getView();
		TicketCriteria criteria = view.getCriteria();
		Results<Ticket> results = null;

		try {
			if (criteria.getId() != null) {
				results = Results.singleEntry(ticketService.findById(criteria.getId()));
			} else {
				results = ticketService.findBy(criteria, view.getPos(), view.getPageSize());
			}
		} catch (YPCException ypce) {
			logger.error(ypce.getMessage(), ypce);
		}

		view.setResultCount(results.getResultCount());
		return new ActionPaneListTableModel<>(TicketTableConstants.COLUMN_NAMES, results.getPage());
	}

}
