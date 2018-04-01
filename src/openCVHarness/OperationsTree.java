package openCVHarness;

import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import operations.ImReadOperation;
import operations.OpenCVOperation;
import passableTypes.IOData;

public class OperationsTree {

    private JTree operationsTree;
    private DefaultMutableTreeNode operationsRootNode;
    private DefaultTreeModel treeModel;

    public OperationsTree() {
        operationsRootNode = new DefaultMutableTreeNode("Operations");
        operationsTree = new JTree(operationsRootNode);
        treeModel = (DefaultTreeModel)operationsTree.getModel();
    }

    public void refreshTree() {
        treeModel.reload();
    }
    
    public ArrayList<OpenCVOperation> getOperationArrayList() {
        int rootChildren = operationsRootNode.getChildCount();
        ArrayList<OpenCVOperation> list = new ArrayList<>();
        for( int i = 0; i < rootChildren; i ++ ) {
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

    public void addOperation( OpenCVOperation operation ) {
      OperationNode opNode = new OperationNode( operation );
      operationsRootNode.add( opNode );
      this.refreshTree();
    }

    public Object getSelectedValue() {
        Object selectedPathComponent = operationsTree.getSelectionPath().getLastPathComponent();
        return selectedPathComponent;
    }

    public class OperationNode extends DefaultMutableTreeNode {
        
        OpenCVOperation operation;
        
        public OperationNode( OpenCVOperation operation ) {
            this.operation = operation;
            createIONodes();
        }
        
        void createIONodes() {
            ArrayList<IOData<?>> dataInputs = operation.getInputs();
            ArrayList<IOData<?>> dataOutputs = operation.getOutputs();
            
            for( IOData<?> data : dataInputs ) {
                IODataNode dataNode = new IODataNode( data );
                this.add(dataNode);;
            }
            
            for( IOData<?> data : dataOutputs ) {
                IODataNode dataNode = new IODataNode( data );
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
        
        public IODataNode( IOData<?> data ) {
            this.data = data;
        }
        
        @Override
        public String toString() {
            switch( data.getIOType() ) {
                case INPUT: {
                    return "Input " + data.getName() + ":";
                }
                case OUTPUT: {
                    return "Output " + data.getName();
                }
                default: {
                    return "IOType error.";
                }
            }
        }
    }

}
