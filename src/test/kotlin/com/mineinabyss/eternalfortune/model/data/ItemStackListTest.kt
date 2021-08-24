package com.mineinabyss.eternalfortune.model.data

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.MockPlugin
import be.seeseemelk.mockbukkit.ServerMock
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.reflect.Type

class ItemStackListTest {
    private lateinit var serverMock: ServerMock
    private lateinit var pluginMock: MockPlugin
    private lateinit var gson: Gson

    private val mapStringAnyType: Type = object : TypeToken<Map<String, Any>>() {}.type

    @BeforeEach
    fun setup() {
        serverMock = MockBukkit.mock()
        pluginMock = MockBukkit.createMockPlugin()
        gson = GsonBuilder().create()
    }

    @Test
    fun `Given a List of ItemStacks, When passed into the List, Then can serialize the list`() {
        val item = ItemStack(Material.DIAMOND_SWORD)
        val dirt = ItemStack(Material.DIRT, 64)

        val stackList = ItemStackList(mutableListOf(item, dirt))

        val json = gson.toJson(stackList.serialize())

        println(json)

        assertThat(
            json, allOf(
                containsString("items"),
                containsString("DIAMOND_SWORD"),
                containsString("DIRT"),
                containsString("64"),
            )
        )
    }

    @Test
    @Ignore("TODO: ItemStack.deserialize doesn't work with MockBukkit apparently, to be checked")
    fun `Given a string JSON, When passed into the List, Then can deserialize to ItemStack list`() {
        val json = """
            {
                "items": [
                    {
                        "type": "WOODEN_AXE",
                        "amount": 1
                    },
                    {
                        "type": "DIAMOND_BLOCK",
                        "amount": 64
                    }
                ]
            }
        """.trimIndent()

        val woodenAxe = ItemStack(Material.WOODEN_AXE)
        val diamondBlocks = ItemStack(Material.DIAMOND_BLOCK, 64)

        val map: Map<String, Any> = gson.fromJson(json, mapStringAnyType)
        println(map)

        val stackList = ItemStackList.deserialize(map)

        assertThat(stackList.itemList, allOf(hasItem(woodenAxe), hasItem(diamondBlocks)))
    }

    @AfterEach
    fun tearDown() {
        MockBukkit.unmock()
    }
}