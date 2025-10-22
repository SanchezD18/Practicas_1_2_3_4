
abstract class Weapon(open val name: String, open val weight: Int) {
    abstract fun isUsable(): Boolean
    abstract fun getUsableMessage(): String
}

open class MeleeWeapon(name: String, weight: Int) : Weapon(name, weight) {
    override fun isUsable(): Boolean = true
    override fun getUsableMessage(): String = "$name usable: true"
}

open class RangedWeapon(name: String, weight: Int, val ammoType: String) : Weapon(name, weight) {
    var ammoCount: Int = 0
    
    override fun isUsable(): Boolean = ammoCount > 0
    override fun getUsableMessage(): String {
        return if (isUsable()) {
            "$name usable: true"
        } else {
            "$name usable: false (no $ammoType)"
        }
    }
    
    fun addAmmo(count: Int) {
        ammoCount += count
    }
}

class Sword(name: String, weight: Int) : MeleeWeapon(name, weight)
class Greatsword(name: String, weight: Int) : MeleeWeapon(name, weight)
class Bow(name: String, weight: Int) : RangedWeapon(name, weight, "arrows")
class Crossbow(name: String, weight: Int) : RangedWeapon(name, weight, "bolts")

class Ammunition(val name: String, val weight: Int, val ammoType: String, val count: Int)

class Player(val name: String, val maxStrength: Int) {
    private val backpack = mutableListOf<Weapon>()
    private val ammo = mutableListOf<Ammunition>()
    private var currentLoad = 0

    fun tryAdd(weapon: Weapon) {
        if (canAdd(weapon)) {
            backpack.add(weapon)
            currentLoad += weapon.weight
            println("Picked ${weapon.name}.")
        } else {
            val excess = (weapon.weight + currentLoad) - maxStrength
            println("Cannot pick ${weapon.name} (+${weapon.weight}). Exceeds capacity by $excess ($currentLoad/$maxStrength).")
        }
    }
    
    fun tryAdd(ammo: Ammunition) {
        if (canAdd(ammo)) {
            this.ammo.add(ammo)
            currentLoad += ammo.weight
            println("Picked ${ammo.name}.")

            backpack.filterIsInstance<RangedWeapon>()
                .filter { it.ammoType == ammo.ammoType }
                .forEach { it.addAmmo(ammo.count) }
        } else {
            val excess = (ammo.weight + currentLoad) - maxStrength
            println("Cannot pick ${ammo.name} (+${ammo.weight}). Exceeds capacity by $excess ($currentLoad/$maxStrength).")
        }
    }

    private fun canAdd(item: Any): Boolean {
        val weight = when (item) {
            is Weapon -> item.weight
            is Ammunition -> item.weight
            else -> 0
        }
        return (weight + currentLoad) <= maxStrength
    }
    
    fun checkWeaponStatus(weapon: Weapon) {
        println(weapon.getUsableMessage())
    }
}

fun main() {
    val player = Player("Aranthor", 20)


    val sword = Sword("Sword", 3)
    val greatsword = Greatsword("Greatsword", 5)
    val bow = Bow("Bow", 2)
    val crossbow = Crossbow("Crossbow", 4)

    val arrows = Ammunition("Arrow x20", 1, "arrows", 20)


    player.checkWeaponStatus(sword)
    player.checkWeaponStatus(greatsword)
    player.checkWeaponStatus(bow)


    player.tryAdd(arrows)


    player.checkWeaponStatus(bow)
    player.checkWeaponStatus(crossbow)
}