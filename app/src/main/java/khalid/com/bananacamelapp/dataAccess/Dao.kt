package khalid.com.bananacamelapp.dataAccess

/**
 * Created by  on 6/26/2019.
 */
class Dao(numberOfBanana: Int, farmToMarketDistance: Int, var noOfCamels: Int, bananaPerKm: Int) {
    var numberOfBanana: Int = 0
    var farmToMarketDistance: Int = 0
    var bananaPerKm: Int = 0
        private set
    var carriedBanana: Int = 0
        set(noOfStoppagePoints) {
            field = numberOfBanana / noOfStoppagePoints
        }
     var distancePerTravel: Int = 0
    var remainingDistance : Int = 0
        private set
    var noOfStoppagePoints: Int = 0
        set(numberOfBanana) {
            field = if (numberOfBanana % 1000 > 0) {
                numberOfBanana / 1000 + 1
            } else {
                numberOfBanana / 1000
            }
        }
    var noOfTimes: Int = 0
        set(numberOfStoppagePoints) {
            field = 2 * numberOfStoppagePoints - noOfCamels
        }

    fun setRemainingDistance(
        distanceToMarket: Int,
        r: Int,
        numberOfTimes: Int,
        numberOfCamel: Int,
        numberOfBananaPerKm: Int
    ) {
        this.remainingDistance = distanceToMarket - r / (numberOfTimes * numberOfBananaPerKm * numberOfCamel)
    }

    fun setR(numberOfBanana: Int, carriedBanana: Int, numberOfStoppagePoints: Int) {
        this.distancePerTravel = numberOfBanana + carriedBanana - carriedBanana * numberOfStoppagePoints
    }

    init {
        this.numberOfBanana = numberOfBanana
        this.farmToMarketDistance = farmToMarketDistance
        this.bananaPerKm = bananaPerKm
    }
    fun getMaxBananas(): Int {
        val maximumBananas: Int
        noOfStoppagePoints = numberOfBanana
        carriedBanana = noOfStoppagePoints
        setR(numberOfBanana, carriedBanana, noOfStoppagePoints)
        noOfTimes = noOfStoppagePoints
        if (numberOfBanana <= farmToMarketDistance) {
            maximumBananas = 0
        } else if (numberOfBanana > farmToMarketDistance && farmToMarketDistance == 0) {
            maximumBananas = numberOfBanana
        } else if (noOfTimes * noOfCamels * carriedBanana / noOfCamels <= distancePerTravel) {
            maximumBananas =
                (numberOfBanana - farmToMarketDistance * noOfStoppagePoints * noOfCamels * bananaPerKm)
        } else {
            setRemainingDistance(
                farmToMarketDistance,
                distancePerTravel,
                noOfTimes,
                noOfCamels,
                bananaPerKm
            )
            noOfStoppagePoints = (numberOfBanana - distancePerTravel)
            numberOfBanana -= distancePerTravel
            farmToMarketDistance = remainingDistance
            noOfTimes = noOfStoppagePoints
            maximumBananas = getMaxBananas()
        }

        return maximumBananas
    }


}
