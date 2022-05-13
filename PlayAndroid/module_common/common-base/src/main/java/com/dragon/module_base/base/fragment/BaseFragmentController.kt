package com.dragon.module_base.base.fragment

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.withState

/**
 * 基础controller
 */
class BaseFragmentController(
    val buildModelsCallback: EpoxyController.() -> Unit = {}
) : AsyncEpoxyController() {
    override fun buildModels() {
        buildModelsCallback()
    }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 */
fun BaseFragment.simpleController(
    buildModels: EpoxyController.() -> Unit
) = BaseFragmentController {
    // Models are built asynchronously, so it is possible that this is called after the fragment
    // is detached under certain race conditions.
    if (view == null || isRemoving) return@BaseFragmentController
    buildModels()
}

/**
 * Create a [BaseFragmentController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MavericksState, A : MavericksViewModel<S>> BaseFragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = BaseFragmentController {
    if (view == null || isRemoving) return@BaseFragmentController
    withState(viewModel) { state ->
        buildModels(state)
    }
}

fun <A : MavericksViewModel<B>, B : MavericksState, C : MavericksViewModel<D>, D : MavericksState> BaseFragment.simpleController(
    viewModel1: A,
    viewModel2: C,
    buildModels: EpoxyController.(state1: B, state2: D) -> Unit
) = BaseFragmentController {
    if (view == null || isRemoving) return@BaseFragmentController
    withState(viewModel1, viewModel2) { state1, state2 ->
        buildModels(state1, state2)
    }
}