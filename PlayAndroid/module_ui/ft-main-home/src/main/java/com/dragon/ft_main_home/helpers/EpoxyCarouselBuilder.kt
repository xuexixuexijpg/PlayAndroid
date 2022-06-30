package com.dragon.ft_main_home.helpers

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.dragon.ft_main_home.views.GridCarouselModelBuilder

import com.dragon.ft_main_home.views.GridCarouselModel_

/**
 * Example that illustrate how to add a builder for nested list (ex: carousel) that allow building
 * it using DSL :
 *
 *   carouselBuilder {
 *     id(...)
 *     for (...) {
 *       carouselItemCustomView {
 *         id(...)
 *       }
 *     }
 *   }
 *
 * @link https://github.com/airbnb/epoxy/issues/847
 */
fun ModelCollector.carouselSnapBuilder(builder: EpoxyCarouselNoSnapBuilder.() -> Unit): GridCarouselModel_ {
    val carouselBuilder = EpoxyCarouselNoSnapBuilder().apply { builder() }
    add(carouselBuilder.carouselNoSnapModel)
    return carouselBuilder.carouselNoSnapModel
}

class EpoxyCarouselNoSnapBuilder(
    internal val carouselNoSnapModel: GridCarouselModel_ = GridCarouselModel_()
) : ModelCollector, GridCarouselModelBuilder by carouselNoSnapModel {
    private val models = mutableListOf<EpoxyModel<*>>()

    override fun add(model: EpoxyModel<*>) {
        models.add(model)

        // Set models list every time a model is added so that it can run debug validations to
        // ensure it is still valid to mutate the carousel model.
        carouselNoSnapModel.models(models)
    }
}
