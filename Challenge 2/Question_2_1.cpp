#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <iomanip>

using namespace std;

class Product {
private:
    string name;
    double price;
    int quantity;

public:
    // Constructor
    Product(string name, double price, int quantity)
        : name(name), price(price), quantity(quantity) {}

    // Getters
    string getName() const { return name; }
    double getPrice() const { return price; }
    int getQuantity() const { return quantity; }

    // Setters
    void setName(const string& newName) { name = newName; }
    void setPrice(double newPrice) { price = newPrice; }
    void setQuantity(int newQuantity) { quantity = newQuantity; }
};

// Calculate total inventory value
double calculateTotalInventoryValue(const vector<Product>& products) {
    double totalValue = 0.0;
    for (int i = 0; i < products.size(); ++i) {
        totalValue += products[i].getPrice() * products[i].getQuantity();
    }
    return totalValue;
}

// Find the most expensive product
string findMostExpensiveProduct(const vector<Product>& products) {
    if (products.empty()) {
        return "None";
    }

    int maxIndex = 0;
    for (int i = 1; i < products.size(); ++i) {
        if (products[i].getPrice() > products[maxIndex].getPrice()) {
            maxIndex = i;
        }
    }
    return products[maxIndex].getName();
}

// Check if a product is in stock by name
bool isProductInStock(const vector<Product>& products, const string& productName) {
    for (int i = 0; i < products.size(); ++i) {
        if (products[i].getName() == productName) {
            return true;
        }
    }
    return false;
}

// Sort products by a given criterion (price or quantity) and order (ascending/descending)
void sortProducts(vector<Product>& products, const string& criterion, bool ascending) {
    if (criterion == "price") {
        sort(products.begin(), products.end(),
            [ascending](const Product& a, const Product& b) {
                return ascending ? a.getPrice() < b.getPrice() : a.getPrice() > b.getPrice();
            });
    } else if (criterion == "quantity") {
        sort(products.begin(), products.end(),
            [ascending](const Product& a, const Product& b) {
                return ascending ? a.getQuantity() < b.getQuantity() : a.getQuantity() > b.getQuantity();
            });
    }
}

// Display all products
void displayProducts(const vector<Product>& products) {
    cout << fixed << setprecision(2);
    for (int i = 0; i < products.size(); ++i) {
        cout << products[i].getName() << ": price " << products[i].getPrice() << ", quantity " << products[i].getQuantity() << '\n';
    }
}

// Read products from file
vector<Product> readProductsFromFile(const string& filename) {
    ifstream file(filename);
    vector<Product> products;
    string line;

    if (!file.is_open()) {
        cerr << "Error: Could not open file " << filename << '\n';
        return products;
    }

    while (getline(file, line)) {
        istringstream iss(line);
        string name;
        double price;
        int quantity;

        // Parse the product details
        int nameEnd = line.find(":");
        if (nameEnd != string::npos) {
            name = line.substr(0, nameEnd);
            int pricePos = line.find("price", nameEnd);
            int quantityPos = line.find("quantity", pricePos);
            if (pricePos != string::npos && quantityPos != string::npos) {
                price = stod(line.substr(pricePos + 6, quantityPos - pricePos - 6));
                quantity = stoi(line.substr(quantityPos + 9));
                products.push_back(Product(name, price, quantity));
            }
        }
    }

    file.close();
    return products;
}

int main() {
    // Read the products from the input file
    string filename = "Question_2_1_input.txt";
    vector<Product> inventory = readProductsFromFile(filename);

    if (inventory.empty()) {
        cout << "No products loaded from file.\n";
        return 1;
    }

    // Calculate total inventory value
    double totalValue = calculateTotalInventoryValue(inventory);
    cout << "Total Inventory Value: " << totalValue << '\n';

    // Find the most expensive product
    string mostExpensiveProduct = findMostExpensiveProduct(inventory);
    cout << "Most Expensive Product: " << mostExpensiveProduct << '\n';

    // Check if "Headphones" is in stock
    bool headphonesInStock = isProductInStock(inventory, "Headphones");
    cout << "Is 'Headphones' in stock: " << (headphonesInStock ? "true" : "false") << '\n';

    // Sort products by price in descending order
    sortProducts(inventory, "price", false);
    cout << "Products sorted by price (descending):\n";
    displayProducts(inventory);

    // Sort products by quantity in ascending order
    sortProducts(inventory, "quantity", true);
    cout << "Products sorted by quantity (ascending):\n";
    displayProducts(inventory);

    return 0;
}
