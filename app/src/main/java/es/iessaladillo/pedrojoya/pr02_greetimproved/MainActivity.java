package es.iessaladillo.pedrojoya.pr02_greetimproved;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr02_greetimproved.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;
    private int timesPressed;
    String treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setDefaultOptions();

        eventsListener();
    }

    //Method that set default options in images, buttons and switches...
    private void setDefaultOptions() {
        //First, we set the app in its freemium model
        binding.lblPremiumSwitch.setChecked(false);
        //First, we are going to suposse the user will prefer Mr treatment
        binding.imgTreatment.setImageResource(R.drawable.ic_mr);
        binding.lblMr.setChecked(true);
        //As we have just started the execution, the progress should be set to 0
        binding.progressGreetsCounter.setProgress(0);
        //We set the max chars counter at 20
        binding.lblCharsLeftName.setText("20 chars");
        binding.lblCharsLeftSurname.setText("20 chars");
    }


    //Method that, depending on what the user presses, makes actions.
    private void eventsListener() {
        binding.lblPremiumSwitch.setOnClickListener(c -> checkPremium());
        binding.btnGreet.setOnClickListener(c -> incrementShow());
        binding.rgdTreatment.setOnCheckedChangeListener((group, checkedId) -> selectTreatment(checkedId));
    }

    //Method that, depending on the times the button was pressed and the pricing model, makes actions
    private void incrementShow() {
        //First, we check if the user has filled the name and surname fields
        if(textsFilled()) {
            //If the button was pressed less than 10 times and the user is freemium, we greet and show times pressed
            if (timesPressed < 10 && !binding.lblPremiumSwitch.isChecked()) {
                timesPressed++;
                showTimesPressed();
                greet();
            } //If the user is premium, no doubts, we go on greeting
            else if (binding.lblPremiumSwitch.isChecked()) {

            } //If the user is not premium and is out of times, tell him to buy premium
            else if (!binding.lblPremiumSwitch.isChecked() && timesPressed >= 10) {
                premiumMessage();
            }
        }
    }

    //Method that greets with Toast
    private void greet() {
        //First, we check if the user select politely greet
        if(binding.chkPolite.isChecked()){
            Toast.makeText(this, "Good morning "+treatment+" "+ binding.txtName.getText()+" "+ binding.txtSurname.getText()+" its a pleasure to meet you", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Eyyyyyyy "+ binding.txtName.getText()+" what's up!", Toast.LENGTH_LONG).show();
        }
    }


    //Method that checks if name and surname fields were filled
    private boolean textsFilled() {
        if(binding.txtName.getText().length()>0 && binding.txtSurname.getText().length()>0){
            return true;
        } else {
            return false;
        }
    }

    //Method that launches a message that tells the user to buy premium
    private void premiumMessage() {
        Toast.makeText(this, R.string.buyPremium, Toast.LENGTH_SHORT).show();
    }

    //Method that executes when the user changes the treatment selected
    private void selectTreatment(int checkedId) {
        //In each case, we take the photo we need and we take the treatment text from the string xml

        if(checkedId == binding.lblMr.getId()){
            binding.imgTreatment.setImageResource(R.drawable.ic_mr);
            treatment = getString(R.string.mr);
        } else if (checkedId == binding.lblMrs.getId()){
            binding.imgTreatment.setImageResource(R.drawable.ic_mrs);
            treatment = getString(R.string.mrs);
        } else {
            binding.imgTreatment.setImageResource(R.drawable.ic_ms);
            treatment = getString(R.string.ms);
        }
    }

    //Method that checks if the Premium switch is pressed, and depending on the result, makes actions.
    private void checkPremium() {
        if(binding.lblPremiumSwitch.isChecked()){
            binding.progressGreetsCounter.setVisibility(View.GONE);
            binding.lblGreetsCounter.setVisibility(View.GONE);
        } else {
            binding.progressGreetsCounter.setVisibility(View.VISIBLE);
            binding.lblGreetsCounter.setVisibility(View.VISIBLE);
            //As the freemium model is set, the counter returns to 0
            timesPressed=0;
            //As the freemium model is set, we need to show the times that the GREET has been used.
            showTimesPressed();
        }
    }

    //Method that show the times the button has been pressed
    private void showTimesPressed() {
        binding.lblGreetsCounter.setText(timesPressed + " of 10");
        binding.progressGreetsCounter.setProgress(timesPressed);
    }

}