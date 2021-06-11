# Hoppa
[![Build][build-shield]][build]
[![Download][download-shield]][download]

_Hoppa_ is a micro utility extension for _AndroidX_ and the _Kotlin Android Extensions_.
It reduces code duplication for simple use-cases around `RecyclerView` and the keyboard.

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


## Synthetic

    implementation 'xyz.tynn.hoppa:synthetic'

### `SyntheticViewHolder` and `SyntheticLayoutHolder`

A simple `RecyclerView.ViewHolder` implementing the `LayoutContainer` interface
to support synthetic layout properties.

    import kotlinx.android.synthetic.main.text_layout.text_view
    holder.text_view.text = "value"

`SyntheticLayoutHolder` extends `SyntheticViewHolder` by adding a reference to
the layout resource used to inflate the layout.


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
