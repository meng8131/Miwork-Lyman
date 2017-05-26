/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */



    public void submitOrder(View view) {

        CheckBox addWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream =addWhippedCream.isChecked();
        CheckBox addChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = addChocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMesssage = createOrderSummary(price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject));
        intent.putExtra(Intent.EXTRA_TEXT, priceMesssage);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream){
            basePrice += 1;
        }
        if (hasChocolate){
            basePrice += 2;
        }
        return basePrice * quantity;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view){
        if  (quantity < 100) {
            quantity += 1;
        } else {
            Toast.makeText(this, "You can'r order more than 100 cups", Toast.LENGTH_SHORT ).show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if (quantity > 1){
            quantity -= 1;
        } else {
            Toast.makeText(this, "You can'r order more less 1 cup", Toast.LENGTH_SHORT ).show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }

    private String createOrderSummary(int price, boolean cream, boolean chocolate) {
        EditText name = (EditText) findViewById(R.id.name_edit_view);
        String summary = name.getText().toString();
//        summary += "\nAdd Whipped Cream?: " + cream;
//        summary += "\nAdd Chocolate?: " + chocolate;
//        summary += "\nQuantity: " + quantity;®
//        summary += "\nTotal: $" + price;
//        summary += "\nThank you!";

        summary += "\n" + getString(R.string.add_toppings_1) + cream;
        summary += "\n" + getString(R.string.add_toppings_2) + chocolate;
        summary += "\n" + getString(R.string.quantity_summary) + quantity;
        summary += "\n" + getString(R.string.total_price_summary) + price;
        summary += "\n" + getString(R.string.thank_you);

        return  summary;
    }

}