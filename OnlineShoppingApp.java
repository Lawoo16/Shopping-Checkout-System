import java.awt.*; 
import java.awt.event.*; 
import java.io.FileWriter; 
import java.io.PrintWriter; 
import java.io.IOException; 

public class OnlineShoppingApp  
{  
    private Frame frame;  
    private Label titleLabel;  
    private List productList;  
    private Button addToCartButton;  
    private Button checkoutButton;  
    private Label cartLabel;  
    private List cartList; 
    private Label totalPriceLabel;  
    private double totalPrice = 0.0; 

    public OnlineShoppingApp() 
    { 
        frame = new Frame("Online Shopping App"); 
        frame.setSize(600, 400);  
        frame.setLayout(new GridLayout(1, 2)); 

        // Left Panel: Products 
        Panel productPanel = new Panel();  
        productPanel.setLayout(new BorderLayout());  
        productPanel.setBackground(new Color(255, 223, 186)); // Light orange background 

        titleLabel = new Label("Available Products:"); 
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Larger and bold font  
        productList = new List(5);  
        
        // Adding products to the list with prices
        productList.add("Tank Top - Rs550");  
        productList.add("Oversized Tee - Rs700"); 
        productList.add("Co-ord Sets - Rs990");  
        productList.add("Blue Flared Jeans - Rs1200"); 
        productList.add("Shirt - Rs700"); 
        productList.add("Black Flared Jeans - Rs1200"); 

        addToCartButton = new Button("Add to Cart");  
        addToCartButton.setBackground(new Color(0, 128, 0)); // Dark green button 

        productPanel.add(titleLabel, BorderLayout.NORTH);  
        productPanel.add(productList, BorderLayout.CENTER);  
        productPanel.add(addToCartButton, BorderLayout.SOUTH); 

        // Right Panel: Shopping Cart  
        Panel cartPanel = new Panel(); 
        cartPanel.setLayout(new BorderLayout());  
        cartPanel.setBackground(new Color(230, 230, 255)); // Light blue background 

        cartLabel = new Label("Shopping Cart:");  
        cartLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Larger and bold font               
        cartList = new List(5); 

        totalPriceLabel = new Label("Total Price: Rs0.00");  
        checkoutButton = new Button("Checkout");  
        checkoutButton.setBackground(new Color(255, 0, 0)); // Dark red button 

        cartPanel.add(cartLabel, BorderLayout.NORTH);  
        cartPanel.add(cartList, BorderLayout.CENTER);  

        // Adding the total price label and checkout button
        Panel southPanel = new Panel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(totalPriceLabel, BorderLayout.CENTER);
        southPanel.add(checkoutButton, BorderLayout.SOUTH);

        cartPanel.add(southPanel, BorderLayout.SOUTH);

        frame.add(productPanel);  
        frame.add(cartPanel); 

        // Action Listener for the Add to Cart button
        addToCartButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) { 
                String selectedProduct = productList.getSelectedItem();  
                if (selectedProduct != null) {
                    cartList.add(selectedProduct);  
                    updateTotalPrice(selectedProduct); 
                } else {
                    showMessage("No Selection", "Please select a product to add to your cart.");
                }
            }
        }); 

        // Action Listener for Checkout button
        checkoutButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                if (totalPrice > 0.0) { 
                    // Save cart data to a text file  
                    try { 
                        FileWriter fileWriter = new FileWriter("shop.txt", true); // Append mode
                        PrintWriter printWriter = new PrintWriter(fileWriter); 

                        String[] items = cartList.getItems(); 
                        for (String item : items) { 
                            printWriter.println(item); 
                        }

                        printWriter.printf("Total Price: Rs%.2f%n", totalPrice);  
                        printWriter.println();  
                        printWriter.close(); 
                    } catch (IOException ex) { 
                        showMessage("Error", "Error saving order details to a file.");  
                    } 

                    String message = "Thank you for your order!\n";  
                    message += "Items in your cart:\n";  
                    String[] items = cartList.getItems();  
                    for (String item : items) { 
                        message += item + "\n"; 
                    } 
                    message += "Total Price: Rs" + String.format("%.2f", totalPrice);  
                    showMessage("Order Confirmation", message);  
                    clearCart();  
                } else { 
                    showMessage("Empty Cart", "Your shopping cart is empty.");  
                } 
            } 
        }); 

        frame.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent we) { 
                System.exit(0); 
            } 
        }); 

        frame.setVisible(true); 
    }  

    // Method to update the total price
    private void updateTotalPrice(String selectedItem) { 
        String[] parts = selectedItem.split(" - Rs"); 
        if (parts.length == 2) { 
            try { 
                double price = Double.parseDouble(parts[1]);  
                totalPrice += price; 
                totalPriceLabel.setText("Total Price: Rs" + String.format("%.2f", totalPrice));  
            } catch (NumberFormatException ignored) { 
                // Handle the exception gracefully
            } 
        } 
    } 

    // Method to clear the cart
    private void clearCart() {  
        cartList.removeAll(); 
        totalPrice = 0.0; 
        totalPriceLabel.setText("Total Price: Rs0.00"); 
    } 

    // Method to display messages
    private void showMessage(String title, String message) { 
        Frame messageFrame = new Frame(title); 
        TextArea textArea = new TextArea(message, 10, 30, TextArea.SCROLLBARS_VERTICAL_ONLY); 
        textArea.setEditable(false);  
        messageFrame.add(textArea);  
        messageFrame.setSize(400, 300);  
        messageFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                messageFrame.dispose();
            }
        });
        messageFrame.setVisible(true); 
    } 

    public static void main(String[] args) { 
        new OnlineShoppingApp();
    }
}