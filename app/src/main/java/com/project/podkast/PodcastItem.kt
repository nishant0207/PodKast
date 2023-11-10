package com.project.podkast

import android.net.Uri
import com.google.firebase.BuildConfig

data class PodcastItem(
    val id: Int,
    val podcastName: String,
    val creatorName: String,
    val category: String,
    val url: Int,
    var view: Int,
    val thumbnail: Int
)


class PodcastManager {
    private val podcasts: List<PodcastItem> = listOf(
        PodcastItem(
            id = 1,
            podcastName = "GaurGopalDas Returns to TRS- Life, Monkhood & Spirituality",
            creatorName = "The Ranveer Show",
            category = "Spiritual",
            url = R.raw.audio,
            2000,
            R.drawable.gaurgopaldas_returns_to_trs_life_monkhood_spirituality
        ),
        PodcastItem(
            id = 2,
            podcastName = "Ayurvedic Expert Dr. Nayana - Skin & Hair Secrets For Indian Bodies",
            creatorName = "The Ranveer Show",
            category = "Ayurveda",
            url = R.raw.ayurvedic_expert_dr_nayana_skinhair_secrets_for_indian_bodies_the_ranveer_show_340,
            300,
            R.drawable.ayurvedic_expert_dr_nayana_skinhair_secrets_for_indian_bodies_the_ranveer_show_340
        ),
        PodcastItem(
            id = 3,
            podcastName = "Cholas, Conquests & Art- Tamil Nadu History Special Raghavan Srinivasan",
            creatorName = "The Ranveer Show",
            category = "History",
            url = R.raw.cholas_conquests_art_tamil_nadu_history_special_raghavan_srinivasan_on_the_ranveer_show_350,
            100,
            R.drawable.cholas_conquests_art_tamil_nadu_history_special_raghavan_srinivasan_on_the_ranveer_show_350
        ),
        PodcastItem(
            id = 4,
            podcastName = "EAM Dr. S. Jaishankar on India & International Relations, Geopolitics & More",
            creatorName = "The Ranveer Show",
            category = "Politics",
            url = R.raw.eam_dr_s_jaishankar_on_india_international_relations_geopolitics_more_the_ranveer_show_314,
            700,
            R.drawable.eam_dr_s_jaishankar_on_india_international_relations_geopolitics_more_the_ranveer_show_314
        ),
        PodcastItem(
            id = 5,
            podcastName = "Geopolitics with Balaji Srinivasan_ Decline of USA, Rise of India_China & More",
            creatorName = "The Ranveer Show",
            category = "Geopolitics",
            url = R.raw.geopolitics_with_balaji_srinivasan__decline_of_usa_rise_of_india_china_more_the_ranveer_show_349,
            50,
            R.drawable.geopolitics_with_balaji_srinivasan__decline_of_usa_rise_of_india_china_more_the_ranveer_show_349
        ),
        PodcastItem(
            id = 6,
            podcastName = "India’s Chief Economic Advisor_ Krishnamurthy S. Opens Up On Indian Govt., Economics & More",
            creatorName = "The Ranveer Show",
            category = "Economics",
            url = R.raw.indias_chief_economic_advisor_krishnamurthy_s_opens_up_on_indian_govt_economics_more_trs_352,
            200,
            R.drawable.indias_chief_economic_advisor_krishnamurthy_s_opens_up_on_indian_govt_economics_more_trs_352
        ),
        PodcastItem(
            id = 7,
            podcastName = "Pakistan's POST Independence History_ 1971 War, From Jinnah To Zia & More, Tilak Devasher",
            creatorName = "The Ranveer Show",
            category = "History",
            url = R.raw.pakistans_post_independence_history_1971_warfrom_jinnah_to_zia_moretilak_devasher_trs_351,
            500,
            R.drawable.pakistans_post_independence_history_1971_warfrom_jinnah_to_zia_moretilak_devasher_trs_351
        ),
        PodcastItem(
            id = 8,
            podcastName = "REAL Kantara_ Significance of Būta Kōlā, Deities & Rituals ft. Rithwik S.",
            creatorName = "The Ranveer Show",
            category = "Dark",
            url = R.raw.eam_dr_s_jaishankar_on_india_international_relations_geopolitics_more_the_ranveer_show_314,
            40,
            R.drawable.real_kantara_significance_of_buta_kola_deities_rituals_ft_rithwik_s_the_ranveer_show_348
        ),
        PodcastItem(
            id = 9,
            podcastName = "SECRETS Of The Universe With Dr. Annapurni Subramaniam_ Life On Mars & More",
            creatorName = "The Ranveer Show",
            category = "Universe",
            url = R.raw.eam_dr_s_jaishankar_on_india_international_relations_geopolitics_more_the_ranveer_show_314,
            60,
            R.drawable.secrets_of_the_universe_with_dr_annapurni_subramaniam_life_on_mars_more_the_ranveer_show_345
        ),
        PodcastItem(
            id = 10,
            podcastName = "Sherpa Amitabh Kant - G20 Summit, Indian Business Opportunities, Growth & Indian Govt",
            creatorName = "The Ranveer Show",
            category = "Business",
            url = R.raw.sherpa_amitabh_kant_g20_summit_indian_business_opportunities_growth_indian_govt_trs_338,
            700,
            R.drawable.sherpa_amitabh_kant_g20_summit_indian_business_opportunities_growth_indian_govt_trs_338
        ),
    )

    fun getTopPodcast(): PodcastItem? {
        return podcasts.maxByOrNull { it.view }
    }

    fun getAllPodcasts(): List<PodcastItem> {
        return podcasts.sortedByDescending { it.view }.drop(1)
    }


    fun getPodcastByName(name: String): PodcastItem? {
        return podcasts.find { it.podcastName.equals(name, ignoreCase = true) }
    }

    fun getPodcastById(podcastId: String?): PodcastItem? {
        return podcasts.find { it.id.toString() == podcastId }
    }

    fun playPodcast(podcastId: Int) {
        var podcast = podcasts.find { it.id == podcastId }
        if (podcast != null) {
            // Increase the view count by 1
            podcast.view++
        }
    }
}