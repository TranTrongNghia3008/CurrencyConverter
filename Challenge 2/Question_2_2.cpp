#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>

using namespace std;

// Function to find the missing number in the aay
int findMissingNumber(const vector<int>& a) {
    int n = a.size(); // Since the aay has n elements, the range is 1 to n+1.
    int expectedSum = (n + 1) * (n + 2) / 2; // Sum of numbers from 1 to n+1.
    
    int actualSum = 0;
    for (int i = 0; i < a.size(); i++) {
        actualSum += a[i];
    }
    
    return expectedSum - actualSum; // The difference is the missing number.
}

int main() {
    // Read the aay from file
    ifstream file("Question_2_2_input.txt");
    vector<int> a;
    string line;

    if (!file.is_open()) {
        cerr << "Error: Could not open file." << endl;
        return 1;
    }

    while (getline(file, line)) {
        istringstream iss(line);
        int num;
        while (iss >> num) {
            a.push_back(num);
        }
    }

    file.close();

    if (a.empty()) {
        cout << "No numbers loaded from file." << endl;
        return 1;
    }

    // Find and print the missing number
    int missingNumber = findMissingNumber(a);
    cout << missingNumber << endl;

    return 0;
}
