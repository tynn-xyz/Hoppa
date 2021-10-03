# Hoppa
[![Build][build-shield]][build]
[![Download][download-shield]][download]

_Hoppa_ is a micro utility extension created around _AndroidX_ and
_Kotlin_ to reduce code duplication for simple use-cases.


## Installation

    repositories {
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        implementation platform("xyz.tynn.hoppa:bom:$hoppaVersion")
    }


## Keyboard

    implementation 'xyz.tynn.hoppa:keyboard'

### `KeyboardVisibilityKt`

A small utility to react to visibility changes of on-screen keyboards.

    activity.hideKeyboardOnFocusChange { it !is EditText }
    activity.setOnKeyboardVisibilityChangeListener(::hideNavigation)


## Flow

    implementation 'xyz.tynn.hoppa:flow'

### `FlowsKt`

Missing builders and operators for `Flow`.

    deferred.asFlow()
    flow.asFlow()
    flow.collectIn(scope) { use(it) }
    flow.stateIn(scope, Eagerly)

#### `Result`

Analogous to `runCatching { ... }`, the `flowCatching()` operator
wraps values and error with the `Result` type. The `flowThrowing`
unwraps the `Result` values and errors again.

    flow.flowCatching().flowThrowing()

### `SharedPreferencesKt`

Builder for flows from `SharedPreferences`.

    prefs.getBooleanFlow("key", false)
    prefs.getFloatFlow("key", 0F)
    prefs.getIntFlow("key", 0)
    prefs.getLongFlow("key", 0L)
    prefs.getStringFlow("key", null)
    prefs.getStringSetFlow("key", null)


## Delegate

    implementation 'xyz.tynn.hoppa:delegate'

### `DelegatesKt`

Extensions to provide mapped data with delegates.

    val foo by delegate.map { long.toString() }
    var foo by delegate.map {
        inverseTransform = { string.toLong() },
        transform = { long.toString() },
    }

    val foo by delegate.nonNull()
    var foo by delegate.nonNull()


### `SharedPreferencesKt`

Delegate extension to access data from `SharedPreferences`.

    var foo by prefs.boolean("key", false)
    var foo by prefs.enum("key", Enum.Value)
    var foo by prefs.float("key", -1F)
    var foo by prefs.int("key", -1)
    var foo by prefs.long("key", -1L)
    var foo by prefs.string("key", "")
    var foo by prefs.stringSet("key", setOf())

    var foo by prefs.nullableEnum<Enum>("key")
    var foo by prefs.nullableString("key")
    var foo by prefs.nullableStringSet("key")


## Storage

    implementation 'xyz.tynn.hoppa:storage'

### `DataStoreKt`

An extension to create a data store from a `Context` and
serializers for the JSON format.

    context.createDataStore(fileName, serializer)
    gson.asSerializer(defaultValue)
    Json.asSerializer(defaultValue)
    moshi.asSerializer(defaultValue)

### `DatabaseUtils`

An extension of `android.database.DatabaseUtils` for `SupportSQLiteStatement`,
`SupportSQLiteDatabase` and `RoomDatabase`.

    roomDb.queryNumEntries(tableName, selection, arg1, arg2)
    db.queryNumEntries(tableName, selection, arg1, arg2)
    db.longForQuery(query, arg1, arg2)
    prog.longForQuery(arg1, arg2)
    db.stringForQuery(query, arg1, arg2)
    prog.stringForQuery(arg1, arg2)
    prog.bindAllArgsAsStrings(arg1, arg2)
    prog.bindObject(index, any)

### `DatabaseBuilderKt`

Extensions to create and configure room databases directly
or using the `DatabaseBuilder` utility.

    databaseBuilder<MyRoomDatabase>("name").build {
        allowMainThreadQueries()
    }
    databaseBuilder.build<MyRoomDatabase>("name") {
        allowMainThreadQueries()
    }
    context.databaseBuilder("name")
    context.buildDatabase("name") {
        allowMainThreadQueries()
    }
    context.inMemoryDatabaseBuilder("name")
    context.buildInMemoryDatabase("name") {
        allowMainThreadQueries()
    }

### `InMemorySharedPreferences`

An in memory implementation of `SharedPreferences` backed by an `ArrayMap`. The call
to `prefs.edit().commit()` always returns false.


## Time

    implementation 'xyz.tynn.hoppa:time'

### `JavaTimeThreeTenMapper`

A set of `java.time` to _ThreeTenBp_ mappers.

    javaTimeDate.toThreeTenBp()
    threeTenBpDate.toJavaTime()


## Recycler

    implementation 'xyz.tynn.hoppa:recycler'

### `DiffUtilItemCallback`

A simple implementation of `DiffUtil.ItemCallback` defaulting to the equality
`oldItem == newItem` for both `areItemsTheSame()` and `areContentsTheSame()`.

    DiffUtilItemCallback()
    DiffUtilItemCallback { oldItem, newItem ->
        oldItem.id == newItem.id
    }
    DiffUtilItemCallback(
        areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )

### `ViewHolderKt`

Some simple delegates to `ViewHolder.itemView` for accessing tags or setting
(long) click listeners.

    holder.context
    holder[tagId] = tagValue
    holder.getTag(tagId)
    holder.setOnClickListener { doSomething() }
    holder.setOnLongClickListener { true }


## Binding

    implementation 'xyz.tynn.hoppa:binding'

### `BindingViewHolder`

A simple `RecyclerView.ViewHolder` providing access to a `ViewBinding`.

    holder.binding.textView.text = "value"

### `Activitykt`

Some simple delegates and extensions to access or use a `ViewBinding`
with an `Activity`.

    activity.contentViewBinding(ResourceBinding::inflate)
    activity.viewBinding(ResourceBinding::inflate)

    activity.setContentView(binding)
    activity.setContentView(ResourceBinding::inflate)

### `FragmentKt`

Some simple delegates and extensions to access or use a `ViewBinding`
with a `Fragment`.

    fragment.viewBinding(ResourceBinding::bind)

    fragment.bind(ResourceBinding::bind)

### `ViewBindingKt`

Some simple delegates to `ViewBinding.root` for accessing tags or setting
(long) click listeners.

    binding.context
    binding[tagId] = tagValue
    binding.getTag(tagId)
    binding.setOnClickListener { doSomething() }
    binding.setOnLongClickListener { true }

### `ViewGroupKt`

A simple extension to inflate a `ViewBinding` with a parent `ViewGroup`.

    parent.inflate(attachToRoot = true, ResourceBinding::inflate)


## Synthetic

    implementation 'xyz.tynn.hoppa:synthetic'

### `SyntheticViewHolder` and `SyntheticLayoutHolder`

A simple `RecyclerView.ViewHolder` implementing the `LayoutContainer` interface to support synthetic
layout properties.

    import kotlinx.android.synthetic.main.text_layout.text_view
    holder.text_view.text = "value"

`SyntheticLayoutHolder` extends `SyntheticViewHolder` by adding a reference to the layout resource
used to inflate the layout.


## License

    Copyright (C) 2020-2021 Christian Schmitz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [build]: https://github.com/tynn-xyz/Hoppa/actions
  [build-shield]: https://img.shields.io/github/workflow/status/tynn-xyz/Hoppa/Build
  [download]: https://jitpack.io/#xyz.tynn/hoppa
  [download-shield]: https://jitpack.io/v/xyz.tynn/hoppa.svg
