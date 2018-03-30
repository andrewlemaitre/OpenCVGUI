package openCVHarness;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListCellRenderer;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;

import miscellaneous.Helper;
import miscellaneous.OperationMenuItem;
import operations.OpenCVOperation;
import operations.OpenCVOperation.OpenCVOperationTransferable;

public class OperationsManager {

    JList<OpenCVOperation> operationsJList;
    DefaultListModel<OpenCVOperation> operationsList;
    
    public OperationsManager() {
        operationsList = new DefaultListModel<>();
        operationsJList = new JList<>( operationsList );
        operationsJList.addMouseListener( new ImageOperationsListListener() );
        operationsJList.setCellRenderer( new ImageOperationsCellRenderer() );
        operationsJList.setTransferHandler( new ListTransferHandler() );
        operationsJList.setDragEnabled( true );
        operationsJList.setDropMode( DropMode.ON_OR_INSERT );
    }
    
    public JList<OpenCVOperation> getOperationsJList() {
        return operationsJList;
    }
    
    public OpenCVOperation getSelectedValue() {
        return operationsJList.getSelectedValue();
    }
    
    public int getSelectedIndex() {
        return operationsJList.getSelectedIndex();
    }
    
    public int getSize() {
        return operationsList.getSize();
    }
    
    public DefaultListModel<OpenCVOperation> getOperationsList() {
        return operationsList;
    }
    
    public OpenCVOperation addOperation( OpenCVOperation operation ) {
        operationsList.addElement(operation);
        return operation;
    }
    
    public OpenCVOperation getElementAt( int index ) {
        return operationsList.getElementAt( index );
    }
    
    public ArrayList<OpenCVOperation> getOperationsArrayList() {
        DefaultListModel<OpenCVOperation> operationsList = this.getOperationsList();
        ArrayList<OpenCVOperation> operationsArrayList = new ArrayList<>();
        for( int i = 0; i < operationsList.size(); i++ ) {
            operationsArrayList.add( operationsList.getElementAt(i));
        }
        return operationsArrayList;
    }

    public void removeElementAt( int index ) {
        operationsList.removeElementAt( index );
    }
    
    public void removeSelectedElement() {
        if( this.getSelectedIndex() >= 0 && this.getSize() > 0)
            this.removeElementAt( this.getSelectedIndex() );
    }
    
    public void copySelectedOperation() {
        if( this.getSelectedIndex() >= 0 && this.getSize() > 0){
          OpenCVOperation operationToCopy = this.getSelectedValue();
          this.addOperation( operationToCopy.newOperationCopy() );
        }
    }
    
    public void runOperations() {
        if( this.getSize() == 0 ) {
            System.err.println("There are no operations to run.");
            return;
        }
        for( int i = 0; i < this.getSize(); i++ )
        {
            this.getElementAt(i).performOperation();
        }
        Helper.getWebcamHarnessWindow().refreshMainView();
    }
    
    public void editSelectedOperation() {
        OpenCVOperation selectedOperation = this.getSelectedValue();
        if( selectedOperation != null ) {
            JDialog dialog = selectedOperation.openDialogBox( );
            
            dialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Helper.getWebcamHarnessWindow().refreshMainView();
                }
            });
            
            dialog.setLocationRelativeTo( Helper.getWebcamHarnessWindow() );
            dialog.setVisible(true);
            dialog.pack();
        } else {
            System.err.println("Edit operation failed because no operation is selected");
        }
    }
    
    public void removeSelectedOperation() {
        
    }
    
    private class ImageOperationsCellRenderer extends JLabel implements ListCellRenderer<OpenCVOperation> {
        public ImageOperationsCellRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getListCellRendererComponent(JList<? extends OpenCVOperation> list, 
                                                        OpenCVOperation value,
                                                        int index, 
                                                        boolean isSelected, 
                                                        boolean cellHasFocus) {
            setText( value.getOperationName() );
            
            Color background;
            Color foreground;
            
            if( isSelected ) {
                background = new Color(184,207,229);
                foreground = Color.BLACK;
                this.setBorder( new LineBorder( new Color( 99, 130, 191 )));
            } else {
                if( value.isValid() ) {
                    background = Color.GREEN;
                    foreground = Color.BLACK;
                    this.setBorder( new LineBorder( Color.GREEN ));
                } else {
                    background = Color.YELLOW;
                    foreground = Color.BLACK;
                    this.setBorder( new LineBorder( Color.YELLOW ));
                }
            }
            
            setBackground( background );
            setForeground( foreground );
            
            return this;
            
        }
    }
    
    //TODO: Make edit operations here and on the button call a editOperation( int i ) function so we have a common code base for creating edit dialogs, showing them and packing them.
    private class ImageOperationsListListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            
            JList<?> list = (JList<?>)evt.getSource();
            
            //If the click count is equal to two and we have clicked the left mouse button.
            if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1 ) {
                int index = list.locationToIndex(evt.getPoint());
                if( index >= 0 ) {
                    JDialog odb = operationsList.getElementAt(index).openDialogBox();
                    odb.pack();
                    java.awt.Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    int x, y;
                    
                    if( mouseLocation.x + odb.getWidth() > screenSize.getWidth() ) {
                        x = (int)screenSize.getWidth()-odb.getWidth();
                    } else {
                        x = mouseLocation.x;
                    }
                    
                    if( mouseLocation.y + odb.getHeight() > screenSize.getHeight() ) {
                        y = (int)screenSize.getHeight()-odb.getHeight();
                    } else {
                        y = mouseLocation.y;
                    }
                        odb.setLocation( x, y );
                        
                    odb.setVisible(true);
                }
            } else if ( list.locationToIndex(evt.getPoint()) >= 0 && evt.getButton() == MouseEvent.BUTTON3 ) {
                int selectedIndex = list.locationToIndex(evt.getPoint());
                
                JPopupMenu jpm = new JPopupMenu();
                JMenu inputOperationsMenu = getInputOperationsForPopupMenu(selectedIndex);
                jpm.add(inputOperationsMenu);
                
                jpm.show( evt.getComponent(), evt.getX(), evt.getY());
            }
            
        }
    }
    
    private class ListTransferHandler extends TransferHandler {
        
        private TransferSupport transferSupport;
        
        public ListTransferHandler() {
            super();
        }
        
        @Override
        public boolean canImport( TransferSupport transferSupport ) {
            // we only import OpenCVOperations
            if (!transferSupport.isDataFlavorSupported(OpenCVOperation.OpenCVOperationTransferable.OPENCV_OPERATION_DATA_FLAVOR))
                return false;
            return true;
        }
        
        @Override
        public boolean importData( TransferSupport transferSupport) {
            
            this.transferSupport = transferSupport;

            if( canImport( transferSupport ) ) {
                return true;
            }
            return false;
        }
        
        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }
        
        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable transferable = null;
            if (c instanceof JList) {
                @SuppressWarnings("unchecked")
                JList<OpenCVOperation> list = (JList<OpenCVOperation>) c;
                Object value = list.getSelectedValue();
                if (value instanceof OpenCVOperation) {
                    OpenCVOperation operation = (OpenCVOperation) value;
                    transferable = operation.getTransferable( list.getSelectedIndex() );
                }
            }
            return transferable;
        }
        
        @Override
        protected void exportDone( JComponent source, Transferable data, int action ) {
            
            JList<?> list = (JList<?>)source;
            @SuppressWarnings("unchecked")
            DefaultListModel<OpenCVOperation> listModel = (DefaultListModel<OpenCVOperation>)list.getModel();
            JList.DropLocation dropLocation;
            int dropIndex;
            int originalIndex = ((OpenCVOperationTransferable)data).getOriginalIndex();
            OpenCVOperation originalOperation = ((OpenCVOperationTransferable)data).getOriginalOperation();
            
            if( transferSupport == null ) {
                return;
            }

            if( !(transferSupport.getComponent() instanceof JList) )
                return;
            
            System.out.println(transferSupport.getComponent());
            dropLocation = (JList.DropLocation)transferSupport.getDropLocation();
            dropIndex = dropLocation.getIndex();
            
            if( dropLocation.isInsert() ) {
                if( dropIndex < originalIndex ) {
                    listModel.removeElement(originalOperation);
                    listModel.add( dropIndex, originalOperation);
                } else if( dropIndex == originalIndex || dropIndex == originalIndex+1 ) {
//                    System.out.println("Dropped into list where it already is.");
                } else {
                    listModel.add( dropIndex, originalOperation);
                    listModel.removeElement(originalOperation);
                }
            } else {
                if( originalIndex > dropIndex ) {
//                    System.out.println("Dropped on another above.");
                } else if ( originalIndex == dropIndex ) {
//                  System.out.println("Dropped on itself.");
                } else {
                    OpenCVOperation targetOperation = listModel.getElementAt(dropIndex);
//                    System.out.println("Dropped on another below.");
//                    System.out.println("Dropped " + originalOperation + " on " + targetOperation);
                    targetOperation.setInputOperation(originalOperation);
                }
            }
        }
    }
    
    private JMenu getInputOperationsForPopupMenu( int selectedIndex )
    {
        //Create a new JMenu
        JMenu inputOperationsMenu = new JMenu("Input Operation");
        
        for( int i = 0; i < selectedIndex; i++ ) {
            //Get each operation up to the selected index (The element that we right clicked in the JList)
            //Create a new OperationMenu item for each operation, add it to the JMenu and set its text to the operation output name.
            OpenCVOperation selectedOperation = operationsList.getElementAt( i );
            JMenuItem newMenuItem = new OperationMenuItem( selectedOperation );
            inputOperationsMenu.add(newMenuItem);
            newMenuItem.setText(selectedOperation.getOutputName());
            
            //Add a listener to the operationmenuitem that will set the inputoperation of the right clicked operation to the selected operationmenuitem.
            newMenuItem.addActionListener( e -> {
                operationsList.getElementAt( selectedIndex ).setInputOperation( ((OperationMenuItem)newMenuItem).getOpenCVOperation() );
            });
        }
        return inputOperationsMenu;
    }
}
