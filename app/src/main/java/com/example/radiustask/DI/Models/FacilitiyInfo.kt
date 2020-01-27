package com.example.radiustask.DI.Models

data class FacilitiyInfo(
    val exclusions: List<List<ExclusionCombinations>>,
    val facilities: List<Facility>
)