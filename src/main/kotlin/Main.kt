package org.example

class Player(val Name: String, val MaxStrength: Int){
    val backpack = mutableListOf<Item>()

    fun CurrentLoad(): Int{
        return backpack.sumOf { it.Weight }
    }

    fun canAdd(objeto: Item): Boolean{
        return (objeto.Weight + CurrentLoad()) < MaxStrength
    }

    fun ListBackpack(){
        print("Backpack: ")
        for (item in backpack){
            print(item.Name +" - ")
        }
    }

    fun TryAdd(objeto: Item){
        if (canAdd(objeto)){
            backpack.add(objeto)
            println("Added ${objeto.Name} (+${objeto.Weight}). Current load: (${CurrentLoad()} / ${MaxStrength})")
        }else{
            println("Cannot add ${objeto.Name} (+${objeto.Weight}) no cabe. Exceeds by ${20 - (CurrentLoad() - objeto.Weight)}.")
        }
    }
}
data class Item(val Name: String, val Weight: Int)

fun main() {
    var Aranthor = Player("Aranthor", 20)
    println("Player: ${Aranthor.Name}, MaxStrength: ${Aranthor.MaxStrength}.")

    var Sword = Item("Sword", 10)
    var Potion = Item("Potion", 3)
    var Treasure = Item("Treasure", 8)

    Aranthor.TryAdd(Sword)
    Aranthor.TryAdd(Potion)
    Aranthor.TryAdd(Treasure)
    Aranthor.ListBackpack()


}