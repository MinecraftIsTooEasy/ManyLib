# Many Lib

---

## 2.0.2

* 在`ValueScreen`屏幕提供一个下拉按钮来切换不同模组[WIP]
* 把模组菜单也改为滑动屏幕[WIP]

---

## 2.0.1

* 支持标签页的双语了
* 优化了`ManyLibConfig`的双语, 避免键名重复
* 改进了信息悬浮显示
* 改进了代码
* 若干性能优化
* 再次删除了`GuiControl`中的ManyLib按钮, 将它移到了ManyLib设置中
* 修复了`ConfigInteger`强转`ConfigDouble`的崩溃问题
* 删除了`LegacyScreen`

---

## 2.0.0

### 新增

* 加入了`ConfigColor`
* `ConfigEnum`加入了`getAllEnumValues(), getOrdinal()`方法

### 修改

* 重构了`ValueScreen`
    * 几乎复刻了`MaLiLib`的配置屏幕
        * 一行只提供一个配置了
        * 加入了滚动条, 当然你也可以用鼠标滚轮
        * 现在`ConfigInteger`和`ConfigDouble`在屏幕中拥有一个按钮来切换是使用文本框编辑还是滑块
        * 现在拥有标签页功能, 开发者可以覆写`getConfigTabs();`来提供多个标签页
        * 加入了搜索功能
        * 加入了排序功能
    * 仍然提供了`LegacyValueScreen`, 如果你的模组想用旧的屏幕, 可以覆写`IConfigHandler`中的`getValueScreen`方法
* `ConfigBoolean`的展示现在改为了绿色的`是`或红色的`否`

### 修复

* `ConfigStringList`无法读取的问题

### 兼容性

* `GuiScreenWithPages`和`GuiScreenWithParent`改名, 去掉了`Gui`
* `IConfigToggle`的`getBooleanValue`改名为`isOn()`了

---

## 1.1.2

### 侵略性更新

* 全面将`List<ConfigBase>`换成了`List<?>`, 不然IDEA警告的标黄太丑了
* 删除了`displayTextFormatter`
* 取消了1.1.1中关于让`ConfigInteger`能够枚举的改动

### 新增

* 加入了真正的`ConfigEnum`
* 双语支持
    * 名称需要`config.name.xxx`
    * 评论需要`config.comment.xxx`
    * `ConfigEnum`的展示名需要`config.enum.xxx.yyy`, 其中`xxx`是配置名称, `yyy`是每个`enum`的名称
    * 在两个Mod列表中的评论需要`config.value.comment.xxx'`和`config.hotkey.comment.xxx'`
* 提供了一个信息悬浮显示接口, 在`RenderUtils.setGuiIngameInfo`中

### 更改

* 加入了`IConfigHandler`接口, 现在`SimpleConfigs`只是它的一个实现
* 将按键设置的按钮从`GuiOptions`移入了`GuiControls`
* 删除了配置 *隐藏按键配置按钮*
* 关于选项屏幕的按钮没对齐的修复, 移回了`ModernMite`, 但设置了`priority=0` 以与`ShaderLoader`兼容
* 优化了`comment`的换行算法, 而且现在可以用`\n`手动换行
* 现在`ConfigDouble`和`ConfigInteger`都是默认使用滑动条了

---

## 1.1.1

### 侵略性更新

* 如果你使用本库配置了快捷键且重写了配置的load()方法,
  那么你可能需要加上一次调用`KeyBinding.resetKeyBindingArrayAndHash()`
  来刷新按键绑定
* 更改了若干个类的目录
* 取消了`CoofigInteger`的`usesSlider`字段,而是使用`ConfigDisplayType`来决定配置形式: 按钮, 滑块, 文本框

### 新增

* 现在本库配置的按键默认在`Minecraft.runTick()`中监听一次, 用户可直接调用`setHotKeyPressCallBack`来编写功能
* 加入了`ConfigString`配置, 可用文本框编辑
* 加入了`ConfigStringList`配置, 只能在文件中编辑
* 加入了`ToggleButton`以供开发者使用(有材质)
* 现在`ConfigInteger`可以用按钮切换, 以获得枚举的效果
* 为`ConfigBoolean`和`ConfigInteger`加入了`displayTextFormatter`, 可以自定义展示文本的内容

### 更改

* 略微优化了按键屏幕的性能, 不再在每次更改按键时都保存配置并调用`KeyBinding.resetKeyBindingArrayAndHash()`了
* 现在用本库配置的按键, 在原版按键控制屏幕上也会检测到冲突并标红了
* 允许各配置的`comment`填`null`了, 取消了无`comment`时默认填入`name`的设定
* 略微调整了`ValueScreen`的排版
* 放宽了一些写死的构造器
* 现在`ConfigInteger`在滑块中可以按四舍五入取值了

### 修复

* 按键 _打开按键配置_ 未被加入配置列表的问题
* 编辑文本框时按Esc不会保存的问题
* 在文本框长按键位时不会连续输入的问题
* 尝试兼容了多系统

---

## 1.1.0

* 请模组作者们注意, 尽快换用更推荐的配置注册方法(`ConfigManager`类)

### 新功能

* 现在重置, 下一页按钮都用图标代替了, 感谢`Xy_Lose`提供的图标
* 仅有一页时, 会隐藏翻页按钮
* 支持滚轮翻页(但我拒绝研究滚动屏幕)
* 重构了各个屏幕的绘画
* 在选项屏幕添加了一个`ManyLib`按键控制按钮(当然, 这可以通过配置隐藏)
* 把`ModernMite`关于选项屏幕的按钮没对齐的修复移至本模组

### 修复

* 游戏中打开`ManyLib`菜单再退出时直接回到游戏主菜单的问题
* 一些代码的优雅重写, 减少了jar包体积(确信)

---

## 1.0.3

### 新功能

* 模组列表的按钮也支持comment了
* 支持用输入框调整数值了, 但前提是开发者指定
* 支持单项配置重置了

### 修复

* 修复了模组列表界面按Esc不会回到上一个屏幕的问题
* 修复了ConfigToggle重置时只重置按键不重置值的问题
* 修复了ConfigDouble滑块在最小值只为0时正确其它都不正确的问题

---

## 1.0.2

### 杂项

* 现在修改配置后, 只在Esc或点击*完成*按钮时保存

### 漏洞修复

* 修复了在本模组的屏幕中按Esc不会回到上一个屏幕的问题
* 修复了在json中读取int, double时可以无视范围的问题

---

## 1.0.1

* 允许自定义屏幕了(因为我要给ITF Reborn画一个自己的屏幕)

---

## 1.0.0

* 最初实现.
