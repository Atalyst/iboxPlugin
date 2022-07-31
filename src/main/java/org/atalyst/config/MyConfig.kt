package org.atalyst.config

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object MyConfig : AutoSavePluginConfig("config"){
    @ValueDescription("要作用的qq群")
    var qqGroup : MutableList<Long> by value()
}