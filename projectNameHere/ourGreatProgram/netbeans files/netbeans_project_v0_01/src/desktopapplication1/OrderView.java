/*
 * DesktopApplication1View.java
 */

package desktopapplication1;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class OrderView extends FrameView {

    public OrderView(SingleFrameApplication app) {
        super(app);

        initComponents();

    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PizzaProgram.getApplication().getMainFrame();
            aboutBox = new DesktopApplication1AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PizzaProgram.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainSplitPane = new javax.swing.JSplitPane();
        customerLookupUI = new javax.swing.JPanel();
        newCustomerButton = new javax.swing.JButton();
        searchCustomerTextArea = new javax.swing.JTextField();
        searchCustomerSearchButton = new javax.swing.JButton();
        customerListScrollPane = new javax.swing.JScrollPane();
        customerList = new javax.swing.JList();
        customerInformationScrollPane = new javax.swing.JScrollPane();
        customerInformationTextArea = new javax.swing.JTextArea();
        selectCustomerButton = new javax.swing.JButton();
        orderCreationUI = new javax.swing.JPanel();
        orderCreationUIVerticalSplitPane = new javax.swing.JSplitPane();
        dishSelectionArea = new javax.swing.JPanel();
        dishTableScrollPane = new javax.swing.JScrollPane();
        dishTable = new javax.swing.JTable();
        addDishButton = new javax.swing.JButton();
        orderOverviewUI = new javax.swing.JPanel();
        orderContentsTableScrollPane = new javax.swing.JScrollPane();
        orderContentsTable = new javax.swing.JTable();
        orderCommentsTextAreaLabel = new javax.swing.JLabel();
        orderCommentsTextAreaScrollPane = new javax.swing.JScrollPane();
        orderCommentsTextArea = new javax.swing.JTextArea();
        resetOrderButton = new javax.swing.JButton();
        confirmOrderButton = new javax.swing.JButton();
        deleteSelectedOrderDishButton = new javax.swing.JButton();
        duplicateSelectedOrderDishButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        openSettingsWindowMenuItem = new javax.swing.JMenuItem();
        openEditMenuWindowMenuItem = new javax.swing.JMenuItem();
        RefreshDataMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        showOrderGUIMenuItem = new javax.swing.JRadioButtonMenuItem();
        showCookGUIMenuItem = new javax.swing.JRadioButtonMenuItem();
        showDeliveryGUIMenuItem = new javax.swing.JRadioButtonMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        GUIMenuItemSelectionButtonGroup = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        mainSplitPane.setDividerLocation(130);
        mainSplitPane.setLastDividerLocation(130);
        mainSplitPane.setName("mainSplitPane"); // NOI18N

        customerLookupUI.setName("customerLookupUI"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.PizzaProgram.class).getContext().getResourceMap(OrderView.class);
        newCustomerButton.setText(resourceMap.getString("newCustomerButton.text")); // NOI18N
        newCustomerButton.setName("newCustomerButton"); // NOI18N

        searchCustomerTextArea.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        searchCustomerTextArea.setText(resourceMap.getString("searchCustomerTextArea.text")); // NOI18N
        searchCustomerTextArea.setToolTipText(resourceMap.getString("searchCustomerTextArea.toolTipText")); // NOI18N
        searchCustomerTextArea.setName("searchCustomerTextArea"); // NOI18N

        searchCustomerSearchButton.setText(resourceMap.getString("searchCustomerSearchButton.text")); // NOI18N
        searchCustomerSearchButton.setName("searchCustomerSearchButton"); // NOI18N

        customerListScrollPane.setName("customerListScrollPane"); // NOI18N

        customerList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        customerList.setName("customerList"); // NOI18N
        customerListScrollPane.setViewportView(customerList);

        customerInformationScrollPane.setName("customerInformationScrollPane"); // NOI18N

        customerInformationTextArea.setColumns(20);
        customerInformationTextArea.setRows(4);
        customerInformationTextArea.setText(resourceMap.getString("customerInformationTextArea.text")); // NOI18N
        customerInformationTextArea.setFocusable(false);
        customerInformationTextArea.setName("customerInformationTextArea"); // NOI18N
        customerInformationScrollPane.setViewportView(customerInformationTextArea);

        selectCustomerButton.setText(resourceMap.getString("selectCustomerButton.text")); // NOI18N
        selectCustomerButton.setName("selectCustomerButton"); // NOI18N

        javax.swing.GroupLayout customerLookupUILayout = new javax.swing.GroupLayout(customerLookupUI);
        customerLookupUI.setLayout(customerLookupUILayout);
        customerLookupUILayout.setHorizontalGroup(
            customerLookupUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerLookupUILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customerLookupUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerLookupUILayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(selectCustomerButton))
                    .addComponent(customerListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(newCustomerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerLookupUILayout.createSequentialGroup()
                        .addComponent(searchCustomerTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchCustomerSearchButton))
                    .addComponent(customerInformationScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        customerLookupUILayout.setVerticalGroup(
            customerLookupUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerLookupUILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newCustomerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerLookupUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCustomerTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchCustomerSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerListScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(customerInformationScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectCustomerButton)
                .addContainerGap())
        );

        mainSplitPane.setLeftComponent(customerLookupUI);

        orderCreationUI.setName("orderCreationUI"); // NOI18N

        orderCreationUIVerticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        orderCreationUIVerticalSplitPane.setName("orderCreationUIVerticalSplitPane"); // NOI18N

        dishSelectionArea.setName("dishSelectionArea"); // NOI18N

        dishTableScrollPane.setName("dishTableScrollPane"); // NOI18N

        dishTable.setAutoCreateRowSorter(true);
        dishTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Pizza", "Extras"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dishTable.setName("dishTable"); // NOI18N
        dishTable.getTableHeader().setReorderingAllowed(false);
        dishTableScrollPane.setViewportView(dishTable);
        dishTable.getColumnModel().getColumn(0).setResizable(false);
        dishTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("dishTable.columnModel.title0")); // NOI18N
        dishTable.getColumnModel().getColumn(1).setResizable(false);
        dishTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("dishTable.columnModel.title1")); // NOI18N

        addDishButton.setText(resourceMap.getString("addDishButton.text")); // NOI18N
        addDishButton.setName("addDishButton"); // NOI18N

        javax.swing.GroupLayout dishSelectionAreaLayout = new javax.swing.GroupLayout(dishSelectionArea);
        dishSelectionArea.setLayout(dishSelectionAreaLayout);
        dishSelectionAreaLayout.setHorizontalGroup(
            dishSelectionAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dishSelectionAreaLayout.createSequentialGroup()
                .addComponent(addDishButton)
                .addGap(652, 652, 652))
            .addComponent(dishTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );
        dishSelectionAreaLayout.setVerticalGroup(
            dishSelectionAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dishSelectionAreaLayout.createSequentialGroup()
                .addComponent(dishTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDishButton))
        );

        orderCreationUIVerticalSplitPane.setTopComponent(dishSelectionArea);

        orderOverviewUI.setName("orderOverviewUI"); // NOI18N

        orderContentsTableScrollPane.setName("orderContentsTableScrollPane"); // NOI18N

        orderContentsTable.setAutoCreateRowSorter(true);
        orderContentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Pizza", "Extras"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        orderContentsTable.setColumnSelectionAllowed(true);
        orderContentsTable.setName("orderContentsTable"); // NOI18N
        orderContentsTableScrollPane.setViewportView(orderContentsTable);
        orderContentsTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        orderContentsTable.getColumnModel().getColumn(0).setResizable(false);
        orderContentsTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("orderContentsTable.columnModel.title0")); // NOI18N
        orderContentsTable.getColumnModel().getColumn(1).setResizable(false);
        orderContentsTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("orderContentsTable.columnModel.title1")); // NOI18N

        orderCommentsTextAreaLabel.setText(resourceMap.getString("orderCommentsTextAreaLabel.text")); // NOI18N
        orderCommentsTextAreaLabel.setName("orderCommentsTextAreaLabel"); // NOI18N

        orderCommentsTextAreaScrollPane.setName("orderCommentsTextAreaScrollPane"); // NOI18N

        orderCommentsTextArea.setColumns(20);
        orderCommentsTextArea.setLineWrap(true);
        orderCommentsTextArea.setRows(2);
        orderCommentsTextArea.setName("orderCommentsTextArea"); // NOI18N
        orderCommentsTextAreaScrollPane.setViewportView(orderCommentsTextArea);

        resetOrderButton.setText(resourceMap.getString("resetOrderButton.text")); // NOI18N
        resetOrderButton.setName("resetOrderButton"); // NOI18N

        confirmOrderButton.setText(resourceMap.getString("confirmOrderButton.text")); // NOI18N
        confirmOrderButton.setName("confirmOrderButton"); // NOI18N

        deleteSelectedOrderDishButton.setText(resourceMap.getString("deleteSelectedOrderDishButton.text")); // NOI18N
        deleteSelectedOrderDishButton.setMargin(new java.awt.Insets(0, 10, 0, 10));
        deleteSelectedOrderDishButton.setName("deleteSelectedOrderDishButton"); // NOI18N

        duplicateSelectedOrderDishButton.setText(resourceMap.getString("duplicateSelectedOrderDishButton.text")); // NOI18N
        duplicateSelectedOrderDishButton.setMargin(new java.awt.Insets(0, 10, 0, 10));
        duplicateSelectedOrderDishButton.setName("duplicateSelectedOrderDishButton"); // NOI18N

        javax.swing.GroupLayout orderOverviewUILayout = new javax.swing.GroupLayout(orderOverviewUI);
        orderOverviewUI.setLayout(orderOverviewUILayout);
        orderOverviewUILayout.setHorizontalGroup(
            orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderOverviewUILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderOverviewUILayout.createSequentialGroup()
                        .addComponent(orderCommentsTextAreaLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                        .addComponent(duplicateSelectedOrderDishButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteSelectedOrderDishButton))
                    .addComponent(orderCommentsTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderOverviewUILayout.createSequentialGroup()
                        .addComponent(resetOrderButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmOrderButton)))
                .addContainerGap())
            .addComponent(orderContentsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
        );
        orderOverviewUILayout.setVerticalGroup(
            orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderOverviewUILayout.createSequentialGroup()
                .addComponent(orderContentsTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deleteSelectedOrderDishButton)
                        .addComponent(duplicateSelectedOrderDishButton))
                    .addComponent(orderCommentsTextAreaLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orderCommentsTextAreaScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(orderOverviewUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmOrderButton)
                    .addComponent(resetOrderButton))
                .addContainerGap())
        );

        orderCreationUIVerticalSplitPane.setRightComponent(orderOverviewUI);

        javax.swing.GroupLayout orderCreationUILayout = new javax.swing.GroupLayout(orderCreationUI);
        orderCreationUI.setLayout(orderCreationUILayout);
        orderCreationUILayout.setHorizontalGroup(
            orderCreationUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(orderCreationUIVerticalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
        );
        orderCreationUILayout.setVerticalGroup(
            orderCreationUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(orderCreationUIVerticalSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );

        mainSplitPane.setRightComponent(orderCreationUI);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainSplitPane)
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.PizzaProgram.class).getContext().getActionMap(OrderView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        openSettingsWindowMenuItem.setText(resourceMap.getString("openSettingsWindowMenuItem.text")); // NOI18N
        openSettingsWindowMenuItem.setName("openSettingsWindowMenuItem"); // NOI18N
        editMenu.add(openSettingsWindowMenuItem);

        openEditMenuWindowMenuItem.setText(resourceMap.getString("openEditMenuWindowMenuItem.text")); // NOI18N
        openEditMenuWindowMenuItem.setName("openEditMenuWindowMenuItem"); // NOI18N
        editMenu.add(openEditMenuWindowMenuItem);

        RefreshDataMenuItem.setText(resourceMap.getString("RefreshDataMenuItem.text")); // NOI18N
        RefreshDataMenuItem.setName("RefreshDataMenuItem"); // NOI18N
        editMenu.add(RefreshDataMenuItem);

        menuBar.add(editMenu);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        GUIMenuItemSelectionButtonGroup.add(showOrderGUIMenuItem);
        showOrderGUIMenuItem.setSelected(true);
        showOrderGUIMenuItem.setText(resourceMap.getString("showOrderGUIMenuItem.text")); // NOI18N
        showOrderGUIMenuItem.setName("showOrderGUIMenuItem"); // NOI18N
        jMenu2.add(showOrderGUIMenuItem);

        GUIMenuItemSelectionButtonGroup.add(showCookGUIMenuItem);
        showCookGUIMenuItem.setText(resourceMap.getString("showCookGUIMenuItem.text")); // NOI18N
        showCookGUIMenuItem.setName("showCookGUIMenuItem"); // NOI18N
        jMenu2.add(showCookGUIMenuItem);

        GUIMenuItemSelectionButtonGroup.add(showDeliveryGUIMenuItem);
        showDeliveryGUIMenuItem.setText(resourceMap.getString("showDeliveryGUIMenuItem.text")); // NOI18N
        showDeliveryGUIMenuItem.setName("showDeliveryGUIMenuItem"); // NOI18N
        jMenu2.add(showDeliveryGUIMenuItem);

        menuBar.add(jMenu2);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GUIMenuItemSelectionButtonGroup;
    private javax.swing.JMenuItem RefreshDataMenuItem;
    private javax.swing.JButton addDishButton;
    private javax.swing.JButton confirmOrderButton;
    private javax.swing.JScrollPane customerInformationScrollPane;
    private javax.swing.JTextArea customerInformationTextArea;
    private javax.swing.JList customerList;
    private javax.swing.JScrollPane customerListScrollPane;
    private javax.swing.JPanel customerLookupUI;
    private javax.swing.JButton deleteSelectedOrderDishButton;
    private javax.swing.JPanel dishSelectionArea;
    private javax.swing.JTable dishTable;
    private javax.swing.JScrollPane dishTableScrollPane;
    private javax.swing.JButton duplicateSelectedOrderDishButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton newCustomerButton;
    private javax.swing.JMenuItem openEditMenuWindowMenuItem;
    private javax.swing.JMenuItem openSettingsWindowMenuItem;
    private javax.swing.JTextArea orderCommentsTextArea;
    private javax.swing.JLabel orderCommentsTextAreaLabel;
    private javax.swing.JScrollPane orderCommentsTextAreaScrollPane;
    private javax.swing.JTable orderContentsTable;
    private javax.swing.JScrollPane orderContentsTableScrollPane;
    private javax.swing.JPanel orderCreationUI;
    private javax.swing.JSplitPane orderCreationUIVerticalSplitPane;
    private javax.swing.JPanel orderOverviewUI;
    private javax.swing.JButton resetOrderButton;
    private javax.swing.JButton searchCustomerSearchButton;
    private javax.swing.JTextField searchCustomerTextArea;
    private javax.swing.JButton selectCustomerButton;
    private javax.swing.JRadioButtonMenuItem showCookGUIMenuItem;
    private javax.swing.JRadioButtonMenuItem showDeliveryGUIMenuItem;
    private javax.swing.JRadioButtonMenuItem showOrderGUIMenuItem;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
}
