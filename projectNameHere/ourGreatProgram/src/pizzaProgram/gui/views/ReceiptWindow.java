/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReceiptWindow.java
 *
 * Created on Nov 2, 2011, 8:39:16 PM
 */
package pizzaProgram.gui.views;

/**
 *
 * @author Bart
 */
public class ReceiptWindow extends javax.swing.JFrame {

    /** Creates new form ReceiptWindow */
    public ReceiptWindow(String receiptString) {
        initComponents();
        this.jEditorPane1.setText(receiptString);
        this.jEditorPane1.setEditable(false);
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setName("Form"); // NOI18N
        jScrollPane1.setViewportView(jEditorPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        setTitle("Receipt Window");
        pack();
        
        setVisible(true);
        setLocation(100, 100);
    }
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
}
