package khalid.com.bananacamelapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import khalid.com.bananacamelapp.dataAccess.Dao
import khalid.com.bananacamelapp.model.Model

/**
 * Created by  on 6/26/2019.
 */
class BananaViewModel: ViewModel(){
    val modelValueLiveData = MutableLiveData<Model>()
    val answerLiveData = MutableLiveData<Int>()
    fun getModel(model: Model){
        modelValueLiveData.postValue(model)
    }
    fun getAnswer(model: Model){
        var maxBanana : Int
        val maximumBanana = Dao(model.nofBanana, model.distance, model.noOfCamel, model.eatsPerKm)
        maximumBanana.apply {
            noOfStoppagePoints = numberOfBanana
           carriedBanana = noOfStoppagePoints
            setR(numberOfBanana, carriedBanana, noOfStoppagePoints)
            noOfTimes = noOfStoppagePoints
            if (numberOfBanana <=farmToMarketDistance){
                maxBanana = 0
            }
            else if (numberOfBanana > farmToMarketDistance && farmToMarketDistance == 0){
                maxBanana = numberOfBanana
            }
            else if ((noOfTimes *noOfCamels *carriedBanana)/noOfCamels <=distancePerTravel){
                maxBanana = numberOfBanana - (farmToMarketDistance * noOfStoppagePoints * noOfCamels * bananaPerKm)
            }
            else{
                setRemainingDistance(farmToMarketDistance, distancePerTravel, noOfTimes, noOfCamels, bananaPerKm)
                noOfStoppagePoints = numberOfBanana - distancePerTravel
                numberOfBanana -= distancePerTravel
                farmToMarketDistance = remainingDistance
                noOfTimes = noOfStoppagePoints
                maxBanana = getMaxBananas()
            }

            answerLiveData.postValue(maxBanana)

        }
    }
}