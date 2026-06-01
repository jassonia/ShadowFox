import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class InventoryItem implements Serializable {

    int id;
    String name;
    String category;
    int quantity;
    double price;
    String supplier;
    String status;

    public InventoryItem(int id, String name, String category,
                         int quantity, double price,
                         String supplier, String status) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
        this.status = status;
    }

    public double totalValue() {
        return quantity * price;
    }
}

public class SmartInventorySystem extends JFrame {

    ArrayList<InventoryItem> inventoryList =
            new ArrayList<>();

    JTable table;
    DefaultTableModel model;

    JTextField txtId, txtName,
            txtQuantity, txtPrice,
            txtSupplier, txtSearch;

    JComboBox<String> txtCategory;

    JLabel totalItemsLabel;
    JLabel totalValueLabel;
    JLabel lowStockLabel;

    final String FILE_NAME = "inventory.dat";

    public SmartInventorySystem() {

        setTitle("SMART INVENTORY MANAGEMENT SYSTEM");

        setSize(1250, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        createHeader();

        createFormPanel();

        createTablePanel();

        createBottomPanel();

        loadData();

        refreshTable();

        updateStatistics();

        setVisible(true);
    }

    private void createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(
                new Color(10, 30, 60)
        );

        header.setPreferredSize(
                new Dimension(100, 90)
        );

        JLabel title = new JLabel(
                "SMART INVENTORY MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        JLabel subtitle = new JLabel(
                "Advanced GUI Based CRUD Application",
                SwingConstants.CENTER
        );

        subtitle.setForeground(
                new Color(200, 220, 255)
        );

        subtitle.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        JPanel textPanel =
                new JPanel(new GridLayout(2, 1));

        textPanel.setBackground(
                new Color(10, 30, 60)
        );

        textPanel.add(title);

        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
    }

    private void createFormPanel() {

        JPanel formPanel =
                new JPanel(new BorderLayout());

        formPanel.setPreferredSize(
                new Dimension(350, 100)
        );

        formPanel.setBackground(
                new Color(245, 245, 245)
        );

        formPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JPanel fieldsPanel =
                new JPanel(
                        new GridLayout(6, 2, 10, 15)
                );

        fieldsPanel.setBackground(
                new Color(245, 245, 245)
        );

        txtId = new JTextField();

        txtName = new JTextField();

        txtCategory =
                new JComboBox<>(new String[]{

                        "Electronics",
                        "Stationery",
                        "Furniture",
                        "Accessories",
                        "Food",
                        "Hardware",
                        "Software"
                });

        txtQuantity = new JTextField();

        txtPrice = new JTextField();

        txtSupplier = new JTextField();

        fieldsPanel.add(createLabel("Item ID"));
        fieldsPanel.add(txtId);

        fieldsPanel.add(createLabel("Item Name"));
        fieldsPanel.add(txtName);

        fieldsPanel.add(createLabel("Category"));
        fieldsPanel.add(txtCategory);

        fieldsPanel.add(createLabel("Quantity"));
        fieldsPanel.add(txtQuantity);

        fieldsPanel.add(createLabel("Price"));
        fieldsPanel.add(txtPrice);

        fieldsPanel.add(createLabel("Supplier"));
        fieldsPanel.add(txtSupplier);

        JButton addButton =
                createButton(
                        "ADD ITEM",
                        new Color(34, 177, 76)
                );

        JButton updateButton =
                createButton(
                        "UPDATE",
                        new Color(0, 102, 204)
                );

        JButton deleteButton =
                createButton(
                        "DELETE",
                        new Color(237, 28, 36)
                );

        JButton clearButton =
                createButton(
                        "CLEAR",
                        new Color(163, 73, 164)
                );

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(2, 2, 15, 15)
                );

        buttonPanel.setBackground(
                new Color(245, 245, 245)
        );

        buttonPanel.setBorder(
                new EmptyBorder(25, 0, 0, 0)
        );

        buttonPanel.add(addButton);

        buttonPanel.add(updateButton);

        buttonPanel.add(deleteButton);

        buttonPanel.add(clearButton);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);

        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.WEST);

        addButton.addActionListener(e -> addItem());

        updateButton.addActionListener(e -> updateItem());

        deleteButton.addActionListener(e -> deleteItem());

        clearButton.addActionListener(e -> clearFields());
    }

    private void createTablePanel() {

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        tablePanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        txtSearch = new JTextField();

        txtSearch.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JButton searchButton =
                createButton(
                        "SEARCH",
                        new Color(255, 127, 39)
                );

        JButton showAllButton =
                createButton(
                        "SHOW ALL",
                        new Color(44, 62, 80)
                );

        topPanel.add(txtSearch, BorderLayout.CENTER);

        JPanel topButtons =
                new JPanel(
                        new GridLayout(1, 2, 10, 0)
                );

        topButtons.add(searchButton);

        topButtons.add(showAllButton);

        topPanel.add(topButtons, BorderLayout.EAST);

        String[] columns = {

                "ID",
                "Name",
                "Category",
                "Quantity",
                "Price",
                "Supplier",
                "Status",
                "Total Value"
        };

        model =
                new DefaultTableModel(columns, 0) {

                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        table = new JTable(model) {

            public Component prepareRenderer(
                    TableCellRenderer renderer,
                    int row,
                    int column
            ) {

                Component c =
                        super.prepareRenderer(
                                renderer,
                                row,
                                column
                        );

                int quantity =
                        Integer.parseInt(
                                getValueAt(row, 3)
                                        .toString()
                        );

                if (isRowSelected(row)) {

                    c.setBackground(
                            new Color(180, 210, 255)
                    );

                    return c;
                }

                if (quantity == 0) {

                    c.setBackground(
                            new Color(255, 102, 102)
                    );
                }

                else if (quantity < 5) {

                    c.setBackground(
                            new Color(255, 204, 102)
                    );
                }

                else {

                    c.setBackground(
                            new Color(204, 255, 204)
                    );
                }

                return c;
            }
        };

        table.setRowHeight(30);

        table.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JTableHeader header =
                table.getTableHeader();

        header.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        header.setBackground(
                new Color(10, 30, 60)
        );

        header.setForeground(Color.WHITE);

        JScrollPane scrollPane =
                new JScrollPane(table);

        tablePanel.add(topPanel, BorderLayout.NORTH);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        searchButton.addActionListener(
                e -> searchItem()
        );

        showAllButton.addActionListener(
                e -> refreshTable()
        );

        table.addMouseListener(
                new MouseAdapter() {

                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        int row =
                                table.getSelectedRow();

                        txtId.setText(
                                model.getValueAt(row, 0)
                                        .toString()
                        );

                        txtName.setText(
                                model.getValueAt(row, 1)
                                        .toString()
                        );

                        txtCategory.setSelectedItem(
                                model.getValueAt(row, 2)
                                        .toString()
                        );

                        txtQuantity.setText(
                                model.getValueAt(row, 3)
                                        .toString()
                        );

                        txtPrice.setText(
                                model.getValueAt(row, 4)
                                        .toString()
                                        .replace("Rs. ", "")
                        );

                        txtSupplier.setText(
                                model.getValueAt(row, 5)
                                        .toString()
                        );
                    }
                }
        );
    }

    private void createBottomPanel() {

        JPanel bottomPanel =
                new JPanel(
                        new GridLayout(1, 3, 20, 20)
                );

        bottomPanel.setBorder(
                new EmptyBorder(10, 20, 20, 20)
        );

        totalItemsLabel =
                createStatCard(
                        "TOTAL ITEMS",
                        "0",
                        new Color(0, 102, 204)
                );

        totalValueLabel =
                createStatCard(
                        "INVENTORY VALUE",
                        "0",
                        new Color(34, 177, 76)
                );

        lowStockLabel =
                createStatCard(
                        "LOW STOCK ITEMS",
                        "0",
                        new Color(255, 127, 39)
                );

        bottomPanel.add(totalItemsLabel.getParent());

        bottomPanel.add(totalValueLabel.getParent());

        bottomPanel.add(lowStockLabel.getParent());

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JLabel createStatCard(
            String title,
            String value,
            Color color
    ) {

        JPanel card =
                new JPanel(new GridLayout(2, 1));

        card.setBackground(color);

        card.setBorder(
                new EmptyBorder(15, 15, 15, 15)
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setForeground(Color.WHITE);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        JLabel valueLabel =
                new JLabel(
                        value,
                        SwingConstants.CENTER
                );

        valueLabel.setForeground(Color.WHITE);

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        card.add(titleLabel);

        card.add(valueLabel);

        return valueLabel;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        return label;
    }

    private JButton createButton(
            String text,
            Color color
    ) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);

        button.setBackground(color);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        Color.WHITE,
                        2
                )
        );

        button.setPreferredSize(
                new Dimension(160, 45)
        );

        button.addMouseListener(
                new MouseAdapter() {

                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                color.darker()
                        );
                    }

                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(color);
                    }
                }
        );

        return button;
    }

    private String getStatus(int quantity) {

        if (quantity == 0) {

            return "Out of Stock";
        }

        else if (quantity < 5) {

            return "Low Stock";
        }

        else {

            return "Available";
        }
    }

    private void addItem() {

        try {

            int id =
                    Integer.parseInt(txtId.getText());

            String name =
                    txtName.getText();

            String category =
                    txtCategory
                            .getSelectedItem()
                            .toString();

            int quantity =
                    Integer.parseInt(
                            txtQuantity.getText()
                    );

            double price =
                    Double.parseDouble(
                            txtPrice.getText()
                    );

            String supplier =
                    txtSupplier.getText();

            String status =
                    getStatus(quantity);

            InventoryItem item =
                    new InventoryItem(
                            id,
                            name,
                            category,
                            quantity,
                            price,
                            supplier,
                            status
                    );

            inventoryList.add(item);

            saveData();

            refreshTable();

            updateStatistics();

            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Item Added Successfully"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void updateItem() {

        try {

            int id =
                    Integer.parseInt(txtId.getText());

            for (InventoryItem item : inventoryList) {

                if (item.id == id) {

                    item.name =
                            txtName.getText();

                    item.category =
                            txtCategory
                                    .getSelectedItem()
                                    .toString();

                    item.quantity =
                            Integer.parseInt(
                                    txtQuantity.getText()
                            );

                    item.price =
                            Double.parseDouble(
                                    txtPrice.getText()
                            );

                    item.supplier =
                            txtSupplier.getText();

                    item.status =
                            getStatus(item.quantity);

                    saveData();

                    refreshTable();

                    updateStatistics();

                    clearFields();

                    JOptionPane.showMessageDialog(
                            this,
                            "Item Updated Successfully"
                    );

                    return;
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void deleteItem() {

        try {

            int id =
                    Integer.parseInt(txtId.getText());

            Iterator<InventoryItem> iterator =
                    inventoryList.iterator();

            while (iterator.hasNext()) {

                InventoryItem item =
                        iterator.next();

                if (item.id == id) {

                    iterator.remove();

                    saveData();

                    refreshTable();

                    updateStatistics();

                    clearFields();

                    JOptionPane.showMessageDialog(
                            this,
                            "Item Deleted Successfully"
                    );

                    return;
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Input"
            );
        }
    }

    private void searchItem() {

        String keyword =
                txtSearch.getText()
                        .toLowerCase()
                        .trim();

        model.setRowCount(0);

        for (InventoryItem item : inventoryList) {

            if (item.name.toLowerCase()
                    .contains(keyword)

                    ||

                    item.category.toLowerCase()
                            .contains(keyword)

                    ||

                    item.supplier.toLowerCase()
                            .contains(keyword)

                    ||

                    item.status.toLowerCase()
                            .contains(keyword)

                    ||

                    String.valueOf(item.id)
                            .contains(keyword)) {

                model.addRow(new Object[]{

                        item.id,
                        item.name,
                        item.category,
                        item.quantity,
                        "Rs. " + item.price,
                        item.supplier,
                        item.status,
                        "Rs. " + item.totalValue()
                });
            }
        }
    }

    private void refreshTable() {

        model.setRowCount(0);

        for (InventoryItem item : inventoryList) {

            model.addRow(new Object[]{

                    item.id,
                    item.name,
                    item.category,
                    item.quantity,
                    "Rs. " + item.price,
                    item.supplier,
                    item.status,
                    "Rs. " + item.totalValue()
            });
        }
    }

    private void clearFields() {

        txtId.setText("");

        txtName.setText("");

        txtCategory.setSelectedIndex(0);

        txtQuantity.setText("");

        txtPrice.setText("");

        txtSupplier.setText("");
    }

    private void updateStatistics() {

        int totalItems =
                inventoryList.size();

        double totalValue = 0;

        int lowStock = 0;

        for (InventoryItem item : inventoryList) {

            totalValue += item.totalValue();

            if (item.quantity < 5) {

                lowStock++;
            }
        }

        totalItemsLabel.setText(
                String.valueOf(totalItems)
        );

        totalValueLabel.setText(
                "Rs. " + totalValue
        );

        lowStockLabel.setText(
                String.valueOf(lowStock)
        );
    }

    private void saveData() {

        try {

            ObjectOutputStream out =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            out.writeObject(inventoryList);

            out.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error Saving Data"
            );
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {

        try {

            ObjectInputStream in =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            Object obj = in.readObject();

            if (obj instanceof ArrayList<?>) {

                inventoryList =
                        (ArrayList<InventoryItem>) obj;
            }

            in.close();

        } catch (Exception e) {

            inventoryList =
                    new ArrayList<>();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new SmartInventorySystem()
        );
    }
}