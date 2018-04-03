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
import openCVHarness.OperationsTree.OperationNode;
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
                    
                    //Create edit menu item
                    JMenuItem editMenu = new JMenuItem("Edit");
                    editMenu.addActionListener( evt -> OperationsManager.editOperation(((OperationNode)path.getLastPathComponent()).getOperation()));
                    popupMenu.add(editMenu);
                    
                    //Create remove menu item
                    JMenuItem removeMenu = new JMenuItem("Remove");
                    removeMenu.addActionListener( evt -> treeModel.removeNodeFromParent((OperationNode) path.getLastPathComponent()));
                    popupMenu.add(removeMenu);
                    
                    //Create copy menu item
                    JMenuItem copyMenu = new JMenuItem("Copy");
                    copyMenu.addActionListener( evt -> {
                        OpenCVOperation newOperation = ((OperationNode)path.getLastPathComponent()).getOperation().newOperationCopy();
                        OperationsTree.this.addOperation(newOperation);
                    });
                    popupMenu.add(copyMenu);
                    
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else if ( path.getLastPathComponent() instanceof IODataNode ) {
                    System.out.println("Clicked iodatanode.");
                    JPopupMenu popupMenu = new JPopupMenu();
                    IODataNode node = (IODataNode) path.getLastPathComponent();
                    if( node.getIOData().getIOType() == IOType.INPUT ) {
                        System.out.println("data node type of input.");
                        IOData<?> ioData = node.getIOData();
                        OpenCVHarnessWindow whw = Helper.getWebcamHarnessWindow();
                        ArrayList<? extends IOData> list = whw.getListManager().getIODataArrayList( ioData.getClass(), IOType.OUTPUT);
                        int foundIndex = 0;
                        for(int i = 0; i < list.size(); i++) {
                            if( list.get(i).getParent().equals( ioData.getParent() )) {
                                foundIndex = i;
                                break;
                            }
                            if(i == list.size()-1) {
                                System.err.println("IOData Image Mat was not found in the list.");
                                return;
                            }
                        }
                        if(foundIndex == 0) {
                            System.err.println("Parent operation appears to be first operation in the list.. so there will be no source mats available.");
                            return;
                        }

                        JMenu inputMenu = new JMenu("inputMenu");
                        
                        for(int i = 0; i < foundIndex; i++) {
                            JMenuItem menuItem = new JMenuItem( list.get(i).getParent().getOperationName() );
                            IOData<?> iodata = list.get(i);
                            menuItem.addActionListener( evt -> System.out.println("Clicked " + iodata.getParent().getOperationName()));
                            inputMenu.add( menuItem );
                        }
                        
                        popupMenu.add(inputMenu);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        }
    }
    
    private JMenu createOperationsJMenu() {
        JMenu newMenu = new JMenu("Inputs");

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

        JMenuItem testItem = new JMenuItem("Not yet implemented");
        testItem.addActionListener( e -> {
            Helper.getWebcamHarnessWindow().refreshMainView();
        });
        newMenu.add(testItem);
        return newMenu;
    }
}
