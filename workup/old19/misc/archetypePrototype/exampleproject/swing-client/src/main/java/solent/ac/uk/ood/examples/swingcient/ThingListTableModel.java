/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solent.ac.uk.ood.examples.swingcient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import solent.ac.uk.ood.examples.model.Thing;

/**
 *
 * @author cgallen
 */
public class ThingListTableModel extends AbstractTableModel {

    private List<Thing> things = Collections.synchronizedList(new ArrayList<Thing>());

    private Thing e = new Thing();

    private boolean DEBUG = true;

    private String[] columnNames = {"id", "Field_A", "Field_B", "Field_C"};

    public List<Thing> getThings() {
        // copies and returns a copied list (unmodifiable to show is not attached)
        synchronized (things) {
            return Collections.unmodifiableList(new ArrayList<Thing>(things));
        }

    }

    public void setThings(List<Thing> things) {
        synchronized (this.things) {
            this.things.clear();
            this.things.addAll(things);
        }
        //fireTableStructureChanged();
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return things.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        if ( row >= things.size()) {
            return null;
        }
        Thing thing = things.get(row);
        switch (col) {
            case 0:
                return thing.getId();
            case 1:
                return thing.getField_A();
            case 2:
                return thing.getField_B();
            case 3:
                return thing.getField_C();
            default: // Optional
                return null;
        }

    }

    /**
     * JTable uses this method to determine the default renderer/ editor for each cell. If we didn't implement this method, then the last column would contain
     * text ("true"/"false"), rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Don't need to implement this method unless your table's editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        return false;
    }

    /**
     * Don't need to implement this method unless your table's data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value + " (an instance of "
                    + value.getClass() + ")");
        }

        Thing thing = things.get(row);
        switch (col) {
            case 0:
                try {
                    Integer val = Integer.parseInt(value.toString());
                    thing.setId(val);
                } catch (NumberFormatException ex) {
                }
                break;
            case 1:
                thing.setField_A(value.toString());
                break;
            case 2:
                thing.setField_B(value.toString());
                break;
            case 3:
                thing.setField_C(value.toString());
                break;

        }

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
        
        fireTableCellUpdated(row, col);
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
