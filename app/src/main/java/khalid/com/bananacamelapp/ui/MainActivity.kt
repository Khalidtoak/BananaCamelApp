package khalid.com.bananacamelapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import khalid.com.bananacamelapp.R
import khalid.com.bananacamelapp.model.Model
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: BananaViewModel
    private var allBanana = 0
    private var noOfCamels = 0
    private var eatsPerKm = 0
    private var distance = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = ViewModelFactory()
        viewModel =  ViewModelProviders.of(this@MainActivity, viewModelFactory)
            .get(BananaViewModel::class.java)
        calculate.setOnClickListener {
            if (anyOfTheFieldsAreEmpty()) return@setOnClickListener
            initInputVars()
            if (isNotWidthinConstraints()) return@setOnClickListener
            viewModel.getModel(Model(noOfCamels, allBanana, eatsPerKm, distance))
            viewModel.modelValueLiveData.observe(this, Observer {model ->
                //perform calc
                viewModel.getAnswer(model)
            })
            viewModel.answerLiveData.observe(this, Observer {ans->
                if (ans<= 0) txt_output.text = "The camel has eaten all the bananas and cannot get to the farm"
                else txt_output.text = "The farmer will be able to sell $ans bananas at the market"
            })
        }
    }

    private fun initInputVars() {
        allBanana = total_banana_text.text.toString().toInt()
        noOfCamels = camel_no.text.toString().toInt()
        eatsPerKm = ed_eats_per_km.text.toString().toInt()
        distance = total_distance_text.text.toString().toInt()
    }

    private fun anyOfTheFieldsAreEmpty(): Boolean {
        if (total_distance_text.text.toString().isEmpty()) {
            handleErrors(total_distance_text, "farm to market distance cannot be empty")
            return true
        }
        if (total_banana_text.text.toString().isEmpty()) {
            handleErrors(total_banana_text, "total banana cannot be empty")
            return true
        }
        if (ed_eats_per_km.text.toString().isEmpty()) {
            handleErrors(ed_eats_per_km, "Eats per kilometer cannot be empty")
            return true
        }
        if (camel_no.text.toString().isEmpty()) {
            handleErrors(camel_no, "total camels cannot be empty")
            return true
        }
        return false
    }
    private fun isNotWidthinConstraints() : Boolean{
        if (allBanana<3000){
            showToast("The number of bananas cannot be less than 3000")
            return true
        }
        if(noOfCamels <1 || noOfCamels > 10){
            showToast("Number of camels has to be between 1 and 10")
            return true
        }
        if (eatsPerKm <1 || eatsPerKm >10){
            showToast("Eats per kilometre has to be between 1 and 10")
            return true
        }
        if(distance  <1000|| distance >10000){
            showToast("Distance has to be between 1000 and 100000")
            return true
        }
        return false


    }
    private fun showToast(message: String){
        Toast.makeText(this@MainActivity, message,Toast.LENGTH_SHORT).show()
    }


    private fun handleErrors(editText: EditText, message:String){
        editText.error = message
    }
}
