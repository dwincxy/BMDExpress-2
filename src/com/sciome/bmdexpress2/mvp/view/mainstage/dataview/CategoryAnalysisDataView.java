package com.sciome.bmdexpress2.mvp.view.mainstage.dataview;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.sciome.bmdexpress2.mvp.model.BMDExpressAnalysisRow;
import com.sciome.bmdexpress2.mvp.model.category.CategoryAnalysisResult;
import com.sciome.bmdexpress2.mvp.model.category.CategoryAnalysisResults;
import com.sciome.bmdexpress2.mvp.model.category.GOAnalysisResult;
import com.sciome.bmdexpress2.mvp.model.category.PathwayAnalysisResult;
import com.sciome.bmdexpress2.mvp.presenter.mainstage.dataview.CategoryAnalysisDataViewPresenter;
import com.sciome.bmdexpress2.mvp.view.visualization.CategoryAnalysisDataVisualizationView;
import com.sciome.bmdexpress2.mvp.view.visualization.DataVisualizationView;
import com.sciome.bmdexpress2.mvp.viewinterface.mainstage.dataview.IBMDExpressDataView;
import com.sciome.bmdexpress2.shared.BMDExpressConstants;
import com.sciome.bmdexpress2.shared.eventbus.BMDExpressEventBus;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class CategoryAnalysisDataView extends BMDExpressDataView<CategoryAnalysisResults>
		implements IBMDExpressDataView
{

	private Callback<TableColumn, TableCell> categoryCellFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CategoryAnalysisDataView(CategoryAnalysisResults categoryAnalysisResults, String viewTypeKey)
	{
		super(CategoryAnalysisResult.class, categoryAnalysisResults, viewTypeKey);
		presenter = new CategoryAnalysisDataViewPresenter(this, BMDExpressEventBus.getInstance());

		// Create a CellFactory for the category id
		categoryCellFactory = new CategoryTableCallBack();

		setUpTableView(categoryAnalysisResults);
		if (categoryAnalysisResults.getColumnHeader().size() == 0)
			return;
		TableColumn tc = tableView.getColumns().get(0);
		tc.setCellFactory(categoryCellFactory);
		presenter.showVisualizations(categoryAnalysisResults);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void close()
	{
		if (tableView != null && tableView.getColumns().size() > 0)
		{
			TableColumn tc = tableView.getColumns().get(0);
			tc.setCellFactory(null);
		}
		super.close();

	}

	@Override
	protected DataVisualizationView getDataVisualizationView()
	{
		return new CategoryAnalysisDataVisualizationView();
	}

}

final class CategoryTableMousEvent implements EventHandler<MouseEvent>
{

	@Override
	public void handle(MouseEvent event)
	{
		if (event.getClickCount() != 1)
		{
			return;
		}
		TableCell c = (TableCell) event.getSource();
		CategoryAnalysisResult item = (CategoryAnalysisResult) c.getTableRow().getItem();

		if (item == null)
			return;

		try
		{

			if (item instanceof GOAnalysisResult)
				java.awt.Desktop.getDesktop().browse(new URI(
						BMDExpressConstants.getInstance().GO_WEB + item.getCategoryIdentifier().getId()));
			else if (item instanceof PathwayAnalysisResult)
				java.awt.Desktop.getDesktop().browse(new URI(BMDExpressConstants.getInstance().PATHWAY_WEB
						+ item.getCategoryIdentifier().getId()));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

final class CategoryTableCallBack implements Callback<TableColumn, TableCell>
{

	@Override
	public TableCell call(TableColumn param)
	{
		TableCell cell = new TableCell<BMDExpressAnalysisRow, String>() {

			// must override drawing the cell so we can color it blue.
			@Override
			public void updateItem(String item, boolean empty)
			{
				super.updateItem(item, empty);
				setTextFill(javafx.scene.paint.Color.BLUE);
				setText(empty ? null : getString());
				setGraphic(null);
			}

			private String getString()
			{
				return getItem() == null ? "" : getItem().toString();
			}
		};

		// add mouse click event handler.
		cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new CategoryTableMousEvent());
		return cell;
	}

}
