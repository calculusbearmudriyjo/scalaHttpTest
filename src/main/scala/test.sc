trait noFuel
trait hasFuel
trait noO2
trait hasO2

case class Rocket[Fuel, O2]() {}

def createRocket() = Rocket[noFuel, noO2]()
def fillFuel[O2](rocket: Rocket[noFuel, O2]) = Rocket[hasFuel, O2]()
def fillO2[fuel](rocket: Rocket[fuel, noO2]) = Rocket[fuel, hasO2]()
def startRocket(rocket: Rocket[hasFuel, hasO2]) = println("rocket start")

//startRocket(fillFuel(createRocket()))
//fillFuel(fillFuel(createRocket()))
startRocket(fillO2(fillFuel(createRocket())))

val a: List[Tuple2[Int, Int]] = List(1, 2, 3).flatMap(x => List(4,5).map(z => Tuple2(z, x)))