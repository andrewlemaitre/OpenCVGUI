package openCVHarness;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JTree;
import miscellaneous.Helper;
import openCVHarness.OperationsTree.OperationNode;
import operations.OpenCVOperation;
import passableTypes.IOData.IOType;

public class OperationsManager {

    OperationsTree operationsTree = new OperationsTree();

    public OperationsManager() {
    }

    public JTree getOperationsTree() {
        return operationsTree.getOperationsTree();
    }

    public OpenCVOperation addOperation(OpenCVOperation operation) {
        operationsTree.addOperation(operation);
        return operation;
    }
    
    public <T> ArrayList<T> getIODataArrayList(Class<T> classType, IOType ioType) {
        return operationsTree.getIODataArrayList(classType, ioType);
    }

    public ArrayList<OpenCVOperation> getOperationsArrayList() {
        return operationsTree.getOperationArrayList();
    }

    public void copySelectedOperation() {
        Object selected = operationsTree.getSelectedValue();
        if(selected instanceof OperationNode) {
            OpenCVOperation newOperation = ((OperationNode)selected).getOperation().newOperationCopy();
            operationsTree.addOperation(newOperation);
        }
    }

    public void runOperations() {
        ArrayList<OpenCVOperation> opList = operationsTree.getOperationArrayList();
        for(OpenCVOperation operation : opList) {
            operation.performOperation();
        }
    }

    public void editSelectedOperation() {
        Object object = operationsTree.getSelectedValue();
        if(object instanceof OperationNode) {
            OpenCVOperation selectedOperation = ((OperationNode)object).getOperation();
            if(selectedOperation != null) {
                JDialog dialog = selectedOperation.openDialogBox();

                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        Helper.getWebcamHarnessWindow().refreshMainView();
                    }
                });

                dialog.setLocationRelativeTo(Helper.getWebcamHarnessWindow());
                dialog.setVisible(true);
                dialog.pack();
            } else {
                System.err.println("Edit operation failed because no operation is selected");
            }
        } else {
            System.err.println("Selected item is not an operation.");
        }
    }

    public void removeSelectedOperation() {
        Object object = operationsTree.getSelectedValue();
        System.out.println("Remove:" + object + ":" + object.getClass());
        if(object instanceof OperationNode) {
            operationsTree.getTreeModel().removeNodeFromParent( (OperationNode) object);
        }
    }

//    private class ImageOperationsCellRenderer extends JLabel implements ListCellRenderer<OpenCVOperation> {
//        public ImageOperationsCellRenderer() {
//            setOpaque(true);
//        }
//        
//        @Override
//        public Component getListCellRendererComponent(JList<? extends OpenCVOperation> list, 
//                                                        OpenCVOperation value,
//                                                        int index, 
//                                                        boolean isSelected, 
//                                                        boolean cellHasFocus) {
//            setText(value.getOperationName());
//            
//            Color background;
//            Color foreground;
//            
//            if(isSelected) {
//                background = new Color(184,207,229);
//                foreground = Color.BLACK;
//                this.setBorder(new LineBorder( new Color( 99, 130, 191)));
//            } else {
//                if(value.isValid()) {
//                    background = Color.GREEN;
//                    foreground = Color.BLACK;
//                    this.setBorder(new LineBorder( Color.GREEN));
//                } else {
//                    background = Color.YELLOW;
//                    foreground = Color.BLACK;
//                    this.setBorder(new LineBorder( Color.YELLOW));
//                }
//            }
//            
//            setBackground(background);
//            setForeground(foreground);
//            
//            return this;
//            
//        }
//    }

//    //TODO: Make edit operations here and on the button call a editOperation(int i) function so we have a common code base for creating edit dialogs, showing them and packing them.
//    private class ImageOperationsListListener extends MouseAdapter {
//        @Override
//        public void mouseClicked(MouseEvent evt) {
//            
//            JList<?> list = (JList<?>)evt.getSource();
//            
//            //If the click count is equal to two and we have clicked the left mouse button.
//            if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
//                int index = list.locationToIndex(evt.getPoint());
//                if(index >= 0) {
//                    JDialog odb = operationsList.getElementAt(index).openDialogBox();
//                    odb.pack();
//                    java.awt.Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//                    int x, y;
//                    
//                    if(mouseLocation.x + odb.getWidth() > screenSize.getWidth()) {
//                        x = (int)screenSize.getWidth()-odb.getWidth();
//                    } else {
//                        x = mouseLocation.x;
//                    }
//                    
//                    if(mouseLocation.y + odb.getHeight() > screenSize.getHeight()) {
//                        y = (int)screenSize.getHeight()-odb.getHeight();
//                    } else {
//                        y = mouseLocation.y;
//                    }
//                        odb.setLocation(x, y);
//                        
//                    odb.setVisible(true);
//                }
//            } else if (list.locationToIndex(evt.getPoint()) >= 0 && evt.getButton() == MouseEvent.BUTTON3) {
//                int selectedIndex = list.locationToIndex(evt.getPoint());
//                
//                JPopupMenu jpm = new JPopupMenu();
//                JMenu inputOperationsMenu = getInputOperationsForPopupMenu(selectedIndex);
//                jpm.add(inputOperationsMenu);
//                
//                jpm.show( evt.getComponent(), evt.getX(), evt.getY());
//            }
//            
//        }
//    }

//    private class ListTransferHandler extends TransferHandler {
//        
//        private TransferSupport transferSupport;
//        
//        public ListTransferHandler() {
//            super();
//        }
//        
//        @Override
//        public boolean canImport(TransferSupport transferSupport) {
//            // we only import OpenCVOperations
//            if (!transferSupport.isDataFlavorSupported(OpenCVOperation.OpenCVOperationTransferable.OPENCV_OPERATION_DATA_FLAVOR))
//                return false;
//            return true;
//        }
//        
//        @Override
//        public boolean importData( TransferSupport transferSupport) {
//            
//            this.transferSupport = transferSupport;
//
//            if(canImport( transferSupport )) {
//                return true;
//            }
//            return false;
//        }
//        
//        @Override
//        public int getSourceActions(JComponent c) {
//            return TransferHandler.MOVE;
//        }
//        
//        @Override
//        protected Transferable createTransferable(JComponent c) {
//            Transferable transferable = null;
//            if (c instanceof JList) {
//                @SuppressWarnings("unchecked")
//                JList<OpenCVOperation> list = (JList<OpenCVOperation>) c;
//                Object value = list.getSelectedValue();
//                if (value instanceof OpenCVOperation) {
//                    OpenCVOperation operation = (OpenCVOperation) value;
//                    transferable = operation.getTransferable(list.getSelectedIndex());
//                }
//            }
//            return transferable;
//        }
//        
//        @Override
//        protected void exportDone(JComponent source, Transferable data, int action) {
//            
//            JList<?> list = (JList<?>)source;
//            @SuppressWarnings("unchecked")
//            DefaultListModel<OpenCVOperation> listModel = (DefaultListModel<OpenCVOperation>)list.getModel();
//            JList.DropLocation dropLocation;
//            int dropIndex;
//            int originalIndex = ((OpenCVOperationTransferable)data).getOriginalIndex();
//            OpenCVOperation originalOperation = ((OpenCVOperationTransferable)data).getOriginalOperation();
//            
//            if(transferSupport == null) {
//                return;
//            }
//
//            if(!(transferSupport.getComponent() instanceof JList))
//                return;
//            
//            System.out.println(transferSupport.getComponent());
//            dropLocation = (JList.DropLocation)transferSupport.getDropLocation();
//            dropIndex = dropLocation.getIndex();
//            
//            if(dropLocation.isInsert()) {
//                if(dropIndex < originalIndex) {
//                    listModel.removeElement(originalOperation);
//                    listModel.add( dropIndex, originalOperation);
//                } else if(dropIndex == originalIndex || dropIndex == originalIndex+1) {
////                    System.out.println("Dropped into list where it already is.");
//                } else {
//                    listModel.add( dropIndex, originalOperation);
//                    listModel.removeElement(originalOperation);
//                }
//            } else {
//                if(originalIndex > dropIndex) {
////                    System.out.println("Dropped on another above.");
//                } else if (originalIndex == dropIndex) {
////                  System.out.println("Dropped on itself.");
//                } else {
//                    OpenCVOperation targetOperation = listModel.getElementAt(dropIndex);
////                    System.out.println("Dropped on another below.");
////                    System.out.println("Dropped " + originalOperation + " on " + targetOperation);
//                    targetOperation.setInputOperation(originalOperation);
//                }
//            }
//        }
//    }
}
