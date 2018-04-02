package openCVHarness;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import miscellaneous.Helper;
import operations.OpenCVOperation;
import passableTypes.IOData;
import passableTypes.IOData.IOType;

public class OperationsTree {

    private JTree operationsTree;
    private DefaultMutableTreeNode operationsRootNode;
    private DefaultTreeModel treeModel;

    public OperationsTree() {
        operationsRootNode = new DefaultMutableTreeNode("Operations");
        operationsTree = new JTree(operationsRootNode);
        treeModel = (DefaultTreeModel)operationsTree.getModel();
        operationsTree.addMouseListener( new TreeMouseListener() );
    }

    public void refreshTree() {
        treeModel.reload();
    }

    public <T> ArrayList<T> getIODataArrayList( Class<T> classType, IOType ioType ) {
        ArrayList<T> ioDataList = new ArrayList<>();
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        getAllNodes( operationsRootNode, treeNodes );
        
        int treeSize = treeNodes.size();
        for( int i = 0; i < treeSize; i++ ) {
            
            TreeNode node = treeNodes.get(i);
            
            if( node instanceof IODataNode ) {
               if(classType.isAssignableFrom(((IODataNode)node).getIOData().getClass())) {
                   if( ((IODataNode)node).getIOData().getIOType() == ioType || ioType == null ) {
                       ioDataList.add(classType.cast(((IODataNode) node).getIOData()));
                   }
               }
            }
        }
        return ioDataList;
    }
    
    public void getAllNodes( ArrayList<TreeNode> outputList ) {
        getAllNodes(operationsRootNode, outputList);
    }
    
    private void getAllNodes( TreeNode rootNode, ArrayList<TreeNode> outputList ) {
        int nodeChildCount = rootNode.getChildCount();
        TreeNode childNode;
        for( int i = 0; i < nodeChildCount; i++ ) {
            childNode = rootNode.getChildAt(i);
            outputList.add(childNode);
            getAllNodes(childNode, outputList);
        }
    }

    public ArrayList<OpenCVOperation> getOperationArrayList() {
        int rootChildren = operationsRootNode.getChildCount();
        ArrayList<OpenCVOperation> list = new ArrayList<>();
        for(int i = 0; i < rootChildren; i ++) {
            TreeNode node = operationsRootNode.getChildAt(i);
            if(node instanceof OperationNode)
                list.add(((OperationNode)node).getOperation());
        }
        return list;
    }

    public JTree getOperationsTree() {
        return operationsTree;
    }

    public DefaultMutableTreeNode getRootNode() {
        return operationsRootNode;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void addOperation(OpenCVOperation operation) {
      OperationNode opNode = new OperationNode(operation);
//      operationsRootNode.add(opNode);
//      treeModel.nodeChanged(opNode);
      treeModel.insertNodeInto(opNode, operationsRootNode, operationsRootNode.getChildCount());
//      this.refreshTree();
//      operationsTree.expandRow(0);
    }

    public Object getSelectedValue() {
        Object selectedPathComponent = operationsTree.getSelectionPath().getLastPathComponent();
        return selectedPathComponent;
    }

    public class OperationNode extends DefaultMutableTreeNode {

        OpenCVOperation operation;

        public OperationNode(OpenCVOperation operation) {
            this.operation = operation;
            createIONodes();
        }

        void createIONodes() {
            ArrayList<IOData<?>> dataInputs = operation.getInputs();
            ArrayList<IOData<?>> dataOutputs = operation.getOutputs();

            for(IOData<?> data : dataInputs) {
                IODataNode dataNode = new IODataNode(data);
                this.add(dataNode);;
            }

            for(IOData<?> data : dataOutputs) {
                IODataNode dataNode = new IODataNode(data);
                this.add(dataNode);
            }
        }

        public OpenCVOperation getOperation() {
            return operation;
        }

        @Override
        public String toString() {
            return operation.getOperationName();
        }
    }

    public class IODataNode extends DefaultMutableTreeNode {

        IOData<?> data;

        public IODataNode(IOData<?> data) {
            this.data = data;
        }
        
        public IOData<?> getIOData() {
            return data;
        }

        @Override
        public String toString() {
            switch(data.getIOType()) {
                case INPUT: {
                    return data.getParent().getOperationName() + " Input";
//                    return "Input " + data.getName() + ":";
                }
                case OUTPUT: {
                    return data.getParent().getOperationName() + " Output";
//                    return "Output " + data.getName();
                }
                default: {
                    return "IOType error.";
                }
            }
        }
    }

    private class TreeMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if ( e.getButton() == MouseEvent.BUTTON3 ) {
                TreePath path = getOperationsTree().getPathForLocation(e.getX(), e.getY());
                if( path == null )
                    return;
                else if( path.getLastPathComponent() instanceof OperationNode ) {
                    System.out.println("Clicked operation node.");
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenu testMenu = createOperationsJMenu();
                    popupMenu.add(testMenu);
                    popupMenu.add( new JMenuItem("Edit"));
                    popupMenu.add( new JMenuItem("Remove"));
                    popupMenu.add( new JMenuItem("Copy"));
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if ( path.getLastPathComponent() instanceof IODataNode ) {
                    System.out.println("Clicked IODataNode.");
                }
            }
        }
    }

////TODO: Make edit operations here and on the button call a editOperation(int i) function so we have a common code base for creating edit dialogs, showing them and packing them.
//private class ImageOperationsListListener extends MouseAdapter {
//  @Override
//  public void mouseClicked(MouseEvent evt) {
//      
//      JList<?> list = (JList<?>)evt.getSource();
//      
//      //If the click count is equal to two and we have clicked the left mouse button.
//      if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
//          int index = list.locationToIndex(evt.getPoint());
//          if(index >= 0) {
//              JDialog odb = operationsList.getElementAt(index).openDialogBox();
//              odb.pack();
//              java.awt.Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//              Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//              int x, y;
//              
//              if(mouseLocation.x + odb.getWidth() > screenSize.getWidth()) {
//                  x = (int)screenSize.getWidth()-odb.getWidth();
//              } else {
//                  x = mouseLocation.x;
//              }
//              
//              if(mouseLocation.y + odb.getHeight() > screenSize.getHeight()) {
//                  y = (int)screenSize.getHeight()-odb.getHeight();
//              } else {
//                  y = mouseLocation.y;
//              }
//                  odb.setLocation(x, y);
//                  
//              odb.setVisible(true);
//          }
//      } else if (list.locationToIndex(evt.getPoint()) >= 0 && evt.getButton() == MouseEvent.BUTTON3) {
//          int selectedIndex = list.locationToIndex(evt.getPoint());
//          
//          JPopupMenu jpm = new JPopupMenu();
//          JMenu inputOperationsMenu = getInputOperationsForPopupMenu(selectedIndex);
//          jpm.add(inputOperationsMenu);
//          
//          jpm.show( evt.getComponent(), evt.getX(), evt.getY());
//      }
//      
//  }
//}
    
    private JMenu createOperationsJMenu() {
        JMenu newMenu = new JMenu("Select Viewer Input");

////        DefaultListModel<OpenCVOperation> operationsList = Helper.getWebcamHarnessWindow().getListManager().getOperationsList();
//        ArrayList<OpenCVOperation> operationsList = Helper.getWebcamHarnessWindow().getListManager().getOperationsArrayList();
//        for(OpenCVOperation operation : operationsList) {
//            OperationMenuItem newMenuItem = new OperationMenuItem(operation);
//            newMenu.add(newMenuItem);
//            newMenuItem.setText(operation.getOutputName());
//
//            //Add a listener to the newMenuItem that will set the inputOperation of this ImagePanel to the selected operation.
////            newMenuItem.addActionListener( e -> {
////                inputOperation = newMenuItem.getOpenCVOperation();
////                this.revalidate();
////                this.repaint();
////            });
//        }

        JMenuItem testItem = new JMenuItem("Test1");
        testItem.addActionListener( e -> {
            Helper.getWebcamHarnessWindow().refreshMainView();
        });
        newMenu.add( new JMenuItem("Test1"));
        return newMenu;
    }
}
