
    interface IPickable{
        fun getName(): String
        fun getWeight(): Double
        fun getDamage(): Double
        fun getSlots(): Int
    }
    interface IWeapon : IPickable



    abstract class WeaponDecorator(protected val weapon: IPickable) : IWeapon {
        override fun getName(): String = weapon.getName()
        override fun getWeight(): Double = weapon.getWeight()
        override fun getDamage(): Double = weapon.getDamage()
        override fun getSlots(): Int = weapon.getSlots()
    }


    class IronWeapon(private val name: String, private val weight: Double, private val damage: Double, private val slots: Int) : IWeapon {
        override fun getName(): String = name
        override fun getWeight(): Double = weight
        override fun getDamage(): Double = damage
        override fun getSlots(): Int = slots
    }

    class Steel(weapon: IWeapon) : WeaponDecorator(weapon) {
        override fun getName(): String = "Steel ${weapon.getName()}"
        override fun getDamage(): Double = (weapon.getDamage() * 1.1)
        override fun getWeight(): Double = weapon.getWeight() * 0.9
    }

    class Mithril(weapon: IWeapon) : WeaponDecorator(weapon) {
        override fun getName(): String = "Mithril ${weapon.getName()}"
        override fun getWeight(): Double = weapon.getWeight() * 0.75
        override fun getDamage(): Double = weapon.getDamage() * 1.2
    }

    class Epic(weapon: IWeapon) : WeaponDecorator(weapon) {
        override fun getName(): String = "Epic ${weapon.getName()}"
        override fun getDamage(): Double = weapon.getDamage() * 1.3
    }



    class Rare(weapon: IWeapon) : WeaponDecorator(weapon) {
        override fun getName(): String = "Heavy ${weapon.getName()}"
        override fun getWeight(): Double = weapon.getWeight() + 2
        override fun getDamage(): Double = weapon.getDamage() + 4
    }

    class Player(val name: String, private val maxLoad: Int, private val maxSlots: Int) {
        private val weapons = mutableListOf<IWeapon>()
        private var currentLoad = 0.0
        private var currentSlots = 0
    
    fun tryAdd(weapon: IWeapon) {
        if (canAdd(weapon)) {
            weapons.add(weapon)
            currentLoad += weapon.getWeight()
            currentSlots += 1
            println("Picked ${weapon.getName()}. Load: $currentLoad/$maxLoad. Slots: $currentSlots/$maxSlots.")
        } else {
            println("Cannot pick ${weapon.getName()}. Exceeds capacity.")
        }
    }
    
    private fun canAdd(weapon: IWeapon): Boolean {
        return (weapon.getWeight() + currentLoad) <= maxLoad && (currentSlots + 1) <= maxSlots
    }
    
    fun showInventory() {
        println("INVENTORY")
        weapons.forEachIndexed { index, weapon ->
            println("${index + 1}. ${weapon.getName()} (Weight: ${weapon.getWeight()}, Damage: ${weapon.getDamage()})")
        }
    }
}
fun main() {
    val epicMithrilSword = Epic(Mithril(IronWeapon("Sword", 6.0, 5.0, 1)))
    val rareSteelBow = Rare(Steel(IronWeapon("Bow", 2.0, 4.0, 1)))


    println("Created weapon: ${epicMithrilSword.getName()}")
    println("Damage: ${epicMithrilSword.getDamage()}")
    println("Weight: ${epicMithrilSword.getWeight()}")
    println("Created weapon: ${rareSteelBow.getName()}")
    println("Damage: ${rareSteelBow.getDamage()}")
    println("Weight: ${rareSteelBow.getWeight()}")

    val player = Player("Aranthor", 20, 2)
    player.tryAdd(epicMithrilSword)
    player.tryAdd(rareSteelBow)
}
