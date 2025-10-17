package org.example


class Player(val name: String, val maxStrength: Int){
    private val backpack = mutableListOf<IPickable>()


    private fun currentLoad(): Int{
        return backpack.sumOf { it.weight }
    }

    private fun canAdd(objeto: IPickable): Boolean{
        return (objeto.weight + currentLoad()) <= maxStrength
    }

    fun listBackpack(){
        print("Backpack: ")
        val itemNames = backpack.map { it.name }
        println(itemNames.joinToString(", "))
    }

    fun tryAdd(objeto: IPickable){
        if (canAdd(objeto)){
            backpack.add(objeto)
            println("Picked ${objeto.name} (+${objeto.weight}). Current load: ${currentLoad()}/${maxStrength}.")
        }else{
            val excess = (objeto.weight + currentLoad()) - maxStrength
            println("Cannot pick ${objeto.name} (+${objeto.weight}). Exceeds capacity by $excess (${currentLoad()}/${maxStrength}).")
        }
    }
    

}
class Item(override val name: String, override val weight: Int) : IPickable {

    override fun canPick(item: IPickable): Boolean {
        return item.weight <= 10
    }

}

fun main() {
    val aranthor = Player("Aranthor", 15)

    println("Player: ${aranthor.name}, MaxStrength: ${aranthor.maxStrength}.")

    val key = Item("Key", 1)
    val scroll = Item("Scroll", 2)
    val treasure = Item("Treasure", 10)
    val potion = Item("Potion", 4)

    aranthor.tryAdd(key)
    aranthor.tryAdd(scroll)
    aranthor.tryAdd(treasure)
    aranthor.tryAdd(potion)

    aranthor.listBackpack()
}

interface IPickable{
    val name: String
    val weight: Int

    fun canPick(item: IPickable): Boolean
}

