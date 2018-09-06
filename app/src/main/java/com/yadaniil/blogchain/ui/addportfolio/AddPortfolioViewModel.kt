package com.yadaniil.blogchain.ui.addportfolio

import androidx.lifecycle.ViewModel
import com.yadaniil.blogchain.db.models.Portfolio
import com.yadaniil.blogchain.repository.PortfolioRepository
import java.util.*
import javax.inject.Inject


class AddPortfolioViewModel
@Inject constructor(private val repo: PortfolioRepository) : ViewModel() {

    fun addPortfolio(name: String, description: String) {
        return repo.addPortfolio(Portfolio(
                id = 0,
                name = name,
                description = description,
                creationDate = Date(),
                lastUpdateDate = Date()))
    }

}
