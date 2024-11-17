import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopApp {
    private JFrame frame;
    private JPanel loginPanel;
    private JPanel registrationPanel;
    private JPanel adminPanel;
    private JPanel userPanel;
    private JPanel storekeeperPanel;
    private JPanel cartPanel;

    private List<Product> products = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Map<Product, Integer> cart = new HashMap<>();
    private List<Order> orders = new ArrayList<>();

    public ShopApp() {
        frame = new JFrame("Интернет-магазин");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        initLoginPanel();
        initRegistrationPanel();
        initAdminPanel();
        initUserPanel();
        initStorekeeperPanel();
        initCartPanel();

        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("storekeeper", "store123", "storekeeper"));

        frame.add(loginPanel);
        frame.setVisible(true);
    }

    private void initLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Добро пожаловать в Интернет-магазин", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Логин:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Войти");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Зарегистрироваться");
        registerButton.setBackground(new Color(40, 167, 69));
        registerButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        loginPanel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean authenticated = false;
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    authenticated = true;
                    switch (user.getRole()) {
                        case "admin":
                            switchToPanel(adminPanel);
                            break;
                        case "user":
                            refreshUserPanel();
                            switchToPanel(userPanel);
                            break;
                        case "storekeeper":
                            refreshStorekeeperPanel();
                            switchToPanel(storekeeperPanel);
                            break;
                    }
                    break;
                }
            }

            if (!authenticated) {
                JOptionPane.showMessageDialog(frame, "Неверный логин или пароль", "Ошибка авторизации", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> switchToPanel(registrationPanel));
    }

    private void initRegistrationPanel() {
        registrationPanel = new JPanel();
        registrationPanel.setLayout(new GridBagLayout());
        registrationPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Регистрация нового пользователя", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        registrationPanel.add(titleLabel, gbc);

        JLabel usernameLabel = new JLabel("Введите логин:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        registrationPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        registrationPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Введите пароль:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        registrationPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        registrationPanel.add(passwordField, gbc);

        JButton registerButton = new JButton("Зарегистрироваться");
        registerButton.setBackground(new Color(40, 167, 69));
        registerButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        registrationPanel.add(registerButton, gbc);

        JButton backButton = new JButton("Назад");
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        registrationPanel.add(backButton, gbc);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Логин и пароль не должны быть пустыми", "Ошибка регистрации", JOptionPane.ERROR_MESSAGE);
            } else {
                users.add(new User(username, password, "user"));
                JOptionPane.showMessageDialog(frame, "Регистрация успешна. Теперь вы можете войти.");
                switchToPanel(loginPanel);
            }
        });

        backButton.addActionListener(e -> switchToPanel(loginPanel));
    }

    private void initAdminPanel() {
        adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Панель администратора");
        JButton addProductButton = new JButton("Добавить товар");
        JButton addStorekeeperButton = new JButton("Добавить кладовщика");
        JButton logoutButton = new JButton("Выйти");

        adminPanel.add(label);
        adminPanel.add(addProductButton);
        adminPanel.add(addStorekeeperButton);
        adminPanel.add(logoutButton);

        addProductButton.addActionListener(e -> {
            String productName = JOptionPane.showInputDialog(frame, "Введите название товара:");
            String productPriceStr = JOptionPane.showInputDialog(frame, "Введите цену товара:");
            try {
                double productPrice = Double.parseDouble(productPriceStr);
                products.add(new Product(productName, productPrice));
                JOptionPane.showMessageDialog(frame, "Товар успешно добавлен");
                refreshUserPanel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Некорректная цена", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        addStorekeeperButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog(frame, "Введите логин для кладовщика:");
            String password = JOptionPane.showInputDialog(frame, "Введите пароль для кладовщика:");
            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                users.add(new User(username, password, "storekeeper"));
                JOptionPane.showMessageDialog(frame, "Кладовщик успешно добавлен");
            } else {
                JOptionPane.showMessageDialog(frame, "Логин и пароль не должны быть пустыми", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutButton.addActionListener(e -> switchToPanel(loginPanel));
    }

    private void initUserPanel() {
        userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Панель пользователя", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        userPanel.add(label, BorderLayout.NORTH);

        refreshUserPanel();
    }

    private void refreshUserPanel() {
        userPanel.removeAll();
        userPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Панель пользователя", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        userPanel.add(label, BorderLayout.NORTH);

        JPanel productListPanel = new JPanel();
        productListPanel.setLayout(new GridLayout(0, 1, 5, 5));
        productListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Product product : products) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BorderLayout());
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            JLabel productLabel = new JLabel(product.getName() + " - " + product.getPrice() + " руб.");
            JButton addToCartButton = new JButton("Добавить в корзину");
            addToCartButton.addActionListener(e -> {
                cart.put(product, cart.getOrDefault(product, 0) + 1);
                JOptionPane.showMessageDialog(frame, "Товар добавлен в корзину: " + product.getName());
                refreshCartPanel();
            });
            productPanel.add(productLabel, BorderLayout.CENTER);
            productPanel.add(addToCartButton, BorderLayout.EAST);
            productListPanel.add(productPanel);
        }

        JScrollPane scrollPane = new JScrollPane(productListPanel);
        userPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton viewCartButton = new JButton("Перейти в корзину");
        JButton logoutButton = new JButton("Выйти");

        viewCartButton.addActionListener(e -> {
            refreshCartPanel();
            switchToPanel(cartPanel);
        });
        logoutButton.addActionListener(e -> switchToPanel(loginPanel));

        buttonPanel.add(viewCartButton);
        buttonPanel.add(logoutButton);
        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        userPanel.revalidate();
        userPanel.repaint();
    }

    private void initCartPanel() {
        cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Ваша корзина", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        cartPanel.add(label, BorderLayout.NORTH);

        refreshCartPanel();
    }

    private void refreshCartPanel() {
        cartPanel.removeAll();
        cartPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Ваша корзина", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        cartPanel.add(label, BorderLayout.NORTH);

        JPanel cartListPanel = new JPanel();
        cartListPanel.setLayout(new GridLayout(0, 1, 5, 5));
        cartListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        double totalCost = 0;

        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            JPanel cartProductPanel = new JPanel();
            cartProductPanel.setLayout(new BorderLayout());
            cartProductPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            JLabel cartProductLabel = new JLabel(product.getName() + " - " + product.getPrice() + " руб. x " + quantity);
            JButton removeFromCartButton = new JButton("-");
            JButton addToCartButton = new JButton("+");

            removeFromCartButton.addActionListener(e -> {
                if (cart.get(product) > 1) {
                    cart.put(product, cart.get(product) - 1);
                } else {
                    cart.remove(product);
                }
                refreshCartPanel();
            });

            addToCartButton.addActionListener(e -> {
                cart.put(product, cart.get(product) + 1);
                refreshCartPanel();
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(removeFromCartButton);
            buttonPanel.add(addToCartButton);

            cartProductPanel.add(cartProductLabel, BorderLayout.CENTER);
            cartProductPanel.add(buttonPanel, BorderLayout.EAST);
            cartListPanel.add(cartProductPanel);

            totalCost += product.getPrice() * quantity;
        }

        JScrollPane scrollPane = new JScrollPane(cartListPanel);
        cartPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel totalCostLabel = new JLabel("Всего: " + totalCost + " руб.");
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 16));
        buttonPanel.add(totalCostLabel);

        JButton backButton = new JButton("Назад");
        JButton checkoutButton = new JButton("Оформить заказ");

        backButton.addActionListener(e -> switchToPanel(userPanel));
        checkoutButton.addActionListener(e -> {
            if (cart.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Ваша корзина пуста", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } else {
                orders.add(new Order(new HashMap<>(cart)));
                JOptionPane.showMessageDialog(frame, "Заказ оформлен! Спасибо за покупку.");
                cart.clear();
                refreshCartPanel();
                refreshStorekeeperPanel();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(checkoutButton);
        cartPanel.add(buttonPanel, BorderLayout.SOUTH);

        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private void initStorekeeperPanel() {
        storekeeperPanel = new JPanel();
        storekeeperPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Панель кладовщика", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        storekeeperPanel.add(label, BorderLayout.NORTH);

        refreshStorekeeperPanel();
    }

    private void refreshStorekeeperPanel() {
        storekeeperPanel.removeAll();
        storekeeperPanel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Панель кладовщика", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        storekeeperPanel.add(label, BorderLayout.NORTH);

        JPanel orderListPanel = new JPanel();
        orderListPanel.setLayout(new GridLayout(0, 1, 5, 5));
        orderListPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Order order : orders) {
            JPanel orderPanel = new JPanel();
            orderPanel.setLayout(new BorderLayout());
            orderPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            StringBuilder orderDetails = new StringBuilder("Заказ:\n");
            double orderTotal = 0;

            for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double productTotal = product.getPrice() * quantity;
                orderTotal += productTotal;
                orderDetails.append(product.getName()).append(" - ").append(quantity).append(" шт. x ")
                        .append(product.getPrice()).append(" руб. = ").append(productTotal).append(" руб.\n");
            }

            orderDetails.append("Общая стоимость заказа: ").append(orderTotal).append(" руб.");
            JLabel orderLabel = new JLabel("<html>" + orderDetails.toString().replaceAll("\n", "<br>") + "</html>");
            orderPanel.add(orderLabel, BorderLayout.CENTER);
            orderListPanel.add(orderPanel);
        }

        JScrollPane scrollPane = new JScrollPane(orderListPanel);
        storekeeperPanel.add(scrollPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Выйти");
        logoutButton.addActionListener(e -> switchToPanel(loginPanel));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(logoutButton);
        storekeeperPanel.add(buttonPanel, BorderLayout.SOUTH);

        storekeeperPanel.revalidate();
        storekeeperPanel.repaint();
    }

    private void switchToPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShopApp::new);
    }
}

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class Order {
    private Map<Product, Integer> products;

    public Order(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
