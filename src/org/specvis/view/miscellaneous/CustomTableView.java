package org.specvis.view.miscellaneous;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Piotr Dzwiniel on 2015-05-25.
 */

/*
 * Copyright 2014-2016 Piotr Dzwiniel
 *
 * This file is part of Specvis.
 *
 * Specvis is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * Specvis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Specvis; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

public class CustomTableView<T> extends TableView<T> {

    public CustomTableView(String[] columnsNames, String[] fieldsNames, ObservableList<T> data) {
        super();

        TableColumn<T, String>[] tableColumns = new TableColumn[fieldsNames.length];
        for (int i = 0; i < tableColumns.length; i++) {
            tableColumns[i] = new TableColumn(columnsNames[i]);
            tableColumns[i].setCellValueFactory(new PropertyValueFactory(fieldsNames[i]));
        }
        this.getColumns().addAll(tableColumns);
        this.setItems(data);

        /* autoresize column width according to the application window width */
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
