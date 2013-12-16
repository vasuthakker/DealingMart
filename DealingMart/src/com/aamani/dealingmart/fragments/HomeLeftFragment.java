package com.aamani.dealingmart.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aamani.dealingmart.R;
import com.aamani.dealingmart.common.DealingMartConstatns;
import com.aamani.dealingmart.interfaces.OnCategoryItemSelected;

public class HomeLeftFragment extends Fragment {

	private OnCategoryItemSelected onCategorySelectedListener;

	// titles
	private RelativeLayout lifeStyleTitleLayout;
	private RelativeLayout menTitleLayout;
	private RelativeLayout womenTitleLayout;
	private RelativeLayout giftTitleLayout;
	private RelativeLayout bagsAccessoriesTitleLayout;
	private RelativeLayout applianceTitleLayout;

	// lifestyle
	private RelativeLayout lifBeautyAndCosmeticsLayout;
	private RelativeLayout lifJewelleryLayout;
	private RelativeLayout lifClothingLayout;
	private RelativeLayout lifOtherAccessoriesLayout;

	// appliances
	private RelativeLayout appHomeAppiancesLayout;
	private RelativeLayout appHomeDecoreLayout;
	private RelativeLayout appOfficeDecoreLayout;

	// bag and accessories
	private RelativeLayout bagTravellingBagsLayout;
	private RelativeLayout bagLaptopBagsLayout;
	private RelativeLayout bagBagPacksLayout;

	// electronics
	private RelativeLayout elecMobileAccessoriesLayout;
	private RelativeLayout elecTabletLayout;

	// book and stationary
	private RelativeLayout bookSatBookLayout;
	private RelativeLayout bookStaStationaryLayout;

	// gift articles
	private RelativeLayout gifPhotoFrameLayout;
	private RelativeLayout gifGoldenDivinityLayout;
	private RelativeLayout gifCrystalShowpieceLayout;
	private RelativeLayout gifDesignerWatchLayout;
	private RelativeLayout gifSunglassesLayout;
	private RelativeLayout gifHandiCraftLayout;
	private RelativeLayout gifHipFlackLayout;
	private RelativeLayout gifIndianFlagLayout;
	private RelativeLayout gifLordGaneshaLayout;
	private RelativeLayout gifSilverPenLayout;
	private RelativeLayout gifPaintingLayout;

	// men

	private RelativeLayout menCravatLayout;
	private RelativeLayout menCuffLinksLayout;
	private RelativeLayout menSocksLayout;
	private RelativeLayout menSuspenderLayout;
	private RelativeLayout menTiesLayout;
	private RelativeLayout menShirtsLayout;
	private RelativeLayout menTShirtsLayout;
	private RelativeLayout menTuxedaStusLayout;
	private RelativeLayout menShortsLayout;
	private RelativeLayout menTrackPantstLayout;
	private RelativeLayout menCapriLayout;

	// women
	private RelativeLayout womenCapriLayout;
	private RelativeLayout womenJegginsLayout;
	private RelativeLayout womenNightwearLayout;
	private RelativeLayout womenPendantLayout;
	private RelativeLayout womenPyjamasLayout;
	private RelativeLayout womenSlipLayout;
	private RelativeLayout womenTshirtsLayout;
	private RelativeLayout womenTightsLayout;
	private RelativeLayout womenNecklaceLayout;
	private RelativeLayout womenBanglesLayout;
	private RelativeLayout womenBraceletLayout;
	private RelativeLayout womenEarringsLayout;
	private RelativeLayout womenHandBagsLayout;

	// helth and wellness
	private RelativeLayout helthKitLayout;

	// lines
	private View clothingLineView;
	private View otherAccessoriesLineView;

	private View backPacksLineView;

	private View officeDecoreLineView;

	private View goldenDivinityLineView;
	private View crystalLineView;
	private View designerLineView;
	private View sunglassLineView;
	private View handicraftLineView;
	private View hipflackLineView;
	private View indianflagLineView;
	private View lordganeshaLineView;
	private View silverpenLineView;
	private View paintingLineView;

	private View cufflinkLineView;
	private View sockLineView;
	private View suspenderLineView;
	private View tiesLineView;
	private View shirtsLineView;
	private View tShirtsLineView;
	private View tuxedaLineView;
	private View shortLineView;
	private View trackPantsLineView;
	private View capriLineView;

	private View nightWearLineView;
	private View pendantLineView;
	private View pyjamasLineView;
	private View slipLineView;
	private View wTShirtsLineView;
	private View tightsLineView;
	private View necklaceLineView;
	private View bangleLineView;
	private View braceletLineView;
	private View earringsLineView;
	private View handBagsLineView;

	// textview plus
	private TextView lifePlusTextView;
	private TextView menPlusTextView;
	private TextView womenPlusTextView;
	private TextView giftPlusTextView;
	private TextView bagsAccessoriesView;
	private TextView appliancePlusTextView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onCategorySelectedListener = (OnCategoryItemSelected) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnArticleSelectedListener");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_home_left_layout, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();
		// UI initialization

		lifePlusTextView = (TextView) getActivity().findViewById(
				R.id.lifestyle_plus_textview);

		menPlusTextView = (TextView) getActivity().findViewById(
				R.id.men_plus_textview);
		womenPlusTextView = (TextView) getActivity().findViewById(
				R.id.women_plus_textview);
		giftPlusTextView = (TextView) getActivity().findViewById(
				R.id.category_gift_plus_textview);
		bagsAccessoriesView = (TextView) getActivity().findViewById(
				R.id.bagsAccessories_plus_textview);
		appliancePlusTextView = (TextView) getActivity().findViewById(
				R.id.appliance_plus_textview);

		// lifestyle
		lifeStyleTitleLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_lifestyle_title_layout);
		lifBeautyAndCosmeticsLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.category_beauty_and_cosmetics_layout);
		lifJewelleryLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_jewellery_layout);
		lifClothingLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_clothing_layout);
		lifOtherAccessoriesLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.category_other_accessories_layout);

		// appliance
		applianceTitleLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_appliance_title_layout);
		appHomeAppiancesLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_home_appliances_layout);
		appHomeDecoreLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_home_decore_layout);
		appOfficeDecoreLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_office_decore_layout);

		// bag and accessories
		bagsAccessoriesTitleLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.category_bagsAcessories_title_layout);
		bagTravellingBagsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_travelling_bags_layout);
		bagLaptopBagsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_laptop_bags_layout);
		bagBagPacksLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_bagpacks_layout);

		// electronics
		elecMobileAccessoriesLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.category_mobile_and_accessories_layout);
		elecTabletLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_tablet_layout);

		// book and stationary
		bookSatBookLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_book_layout);
		bookStaStationaryLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_stationary_layout);

		// gift layouts
		giftTitleLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_gift_title_layout);
		gifPhotoFrameLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_photo_frame_layout);
		gifGoldenDivinityLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_golden_divinity_layout);
		gifCrystalShowpieceLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.category_crystal_showpiece_layout);
		gifDesignerWatchLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_designer_watch_layout);
		gifSunglassesLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_sunglass_layout);
		gifHandiCraftLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_handicraft_layout);
		gifHipFlackLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_hipflask_layout);
		gifIndianFlagLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_indian_flag_layout);
		gifLordGaneshaLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_lordganensha_layout);
		gifSilverPenLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_silverpen_layout);
		gifPaintingLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_painting_layout);

		// men relative layouts
		menTitleLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_man_title_layout);
		menCravatLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_cravats_layout);
		menCuffLinksLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_cufflinks_layout);
		menSocksLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_soaks_layout);
		menSuspenderLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_suspender_layout);
		menTiesLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_ties_layout);
		menShirtsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_shirts_layout);
		menTShirtsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_t_shirts_layout);
		menTuxedaStusLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_tuxeda_studs_layout);
		menShortsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_shorts_layout);
		menTrackPantstLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_trackpants_layout);
		menCapriLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_capri_layout);

		// women
		womenTitleLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_women_title_layout);
		womenCapriLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_women_capri_layout);
		womenJegginsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_jeggins_layout);
		womenNightwearLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_nightwear_layout);
		womenPendantLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_pendant_layout);
		womenPyjamasLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_pyjamas_layout);
		womenSlipLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_slip_layout);
		womenTshirtsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_ladies_t_shirts_layout);
		womenTightsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_tights_layout);
		womenNecklaceLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_necklace_layout);
		womenBanglesLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_bangle_layout);
		womenBraceletLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_bracelet_layout);
		womenEarringsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_earring_layout);
		womenHandBagsLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_handbags_layout);

		// helth and wellness
		helthKitLayout = (RelativeLayout) getActivity().findViewById(
				R.id.category_kit_layout);

		// line views

		// lifeStyle
		clothingLineView = (View) getActivity()
				.findViewById(R.id.clothing_line);
		otherAccessoriesLineView = (View) getActivity().findViewById(
				R.id.otheraccessories_line);

		// bag and accessories
		backPacksLineView = (View) getActivity().findViewById(
				R.id.backpacks_line);

		// appliances
		officeDecoreLineView = (View) getActivity().findViewById(
				R.id.office_line);

		// gift article
		goldenDivinityLineView = (View) getActivity().findViewById(
				R.id.golden_line);
		crystalLineView = (View) getActivity().findViewById(R.id.crystal_line);
		designerLineView = (View) getActivity()
				.findViewById(R.id.designer_line);
		sunglassLineView = (View) getActivity()
				.findViewById(R.id.sunglass_line);
		handicraftLineView = (View) getActivity().findViewById(
				R.id.handicraft_line);
		hipflackLineView = (View) getActivity()
				.findViewById(R.id.hipflask_line);
		indianflagLineView = (View) getActivity()
				.findViewById(R.id.indian_line);
		lordganeshaLineView = (View) getActivity().findViewById(
				R.id.lordganesha_line);
		silverpenLineView = (View) getActivity().findViewById(
				R.id.silverpen_line);
		paintingLineView = (View) getActivity()
				.findViewById(R.id.painting_line);

		// ment line views
		cufflinkLineView = (View) getActivity()
				.findViewById(R.id.cufflink_line);
		sockLineView = (View) getActivity().findViewById(R.id.soak_line);
		suspenderLineView = (View) getActivity().findViewById(
				R.id.suspender_line);
		tiesLineView = (View) getActivity().findViewById(R.id.ties_line);
		shirtsLineView = (View) getActivity().findViewById(R.id.shirts_line);
		tShirtsLineView = (View) getActivity().findViewById(
				R.id.men_tshirts_line);
		tuxedaLineView = (View) getActivity().findViewById(R.id.tuxeda_line);
		shortLineView = (View) getActivity().findViewById(R.id.shorts_line);
		trackPantsLineView = (View) getActivity().findViewById(
				R.id.trackpents_line);
		capriLineView = (View) getActivity().findViewById(R.id.capri_line);

		// women line Views
		nightWearLineView = (View) getActivity().findViewById(
				R.id.nightwear_line);
		pendantLineView = (View) getActivity().findViewById(R.id.pendant_line);
		pyjamasLineView = (View) getActivity().findViewById(R.id.pyjamas_line);
		slipLineView = (View) getActivity().findViewById(R.id.slip_line);
		wTShirtsLineView = (View) getActivity().findViewById(
				R.id.womenTshirts_line);
		tightsLineView = (View) getActivity().findViewById(R.id.tights_line);
		necklaceLineView = (View) getActivity()
				.findViewById(R.id.necklace_line);
		bangleLineView = (View) getActivity().findViewById(R.id.bangle_line);
		braceletLineView = (View) getActivity()
				.findViewById(R.id.bracelet_line);
		earringsLineView = (View) getActivity().findViewById(R.id.earring_line);
		handBagsLineView = (View) getActivity()
				.findViewById(R.id.handbags_line);

		// **listeners

		lifBeautyAndCosmeticsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_LIFESTYLE,
				DealingMartConstatns.SUBCATEGORY_BEAUTY_AND_COSMETICS));
		lifJewelleryLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_LIFESTYLE,
				DealingMartConstatns.SUBCATEGORY_JEWELLERY));
		lifClothingLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_LIFESTYLE,
				DealingMartConstatns.SUBCATEGORY_CLOTHING));
		lifOtherAccessoriesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_LIFESTYLE,
				DealingMartConstatns.SUBCATEGORY_OTHER_ACCESSORIES));

		appHomeAppiancesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_APPLIANCE,
				DealingMartConstatns.SUBCATEGORY_HOME_APPLIANCES));
		appHomeDecoreLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_APPLIANCE,
				DealingMartConstatns.SUBCATEGORY_HOME_DECORE));
		appOfficeDecoreLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_APPLIANCE,
				DealingMartConstatns.SUBCATEGORY_OFFICE_DECORE));

		bagTravellingBagsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_BAGS_ACCESSORIES,

				DealingMartConstatns.SUBCATEGORY_TRAVELLING_BAGS));
		bagLaptopBagsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_BAGS_ACCESSORIES,
				DealingMartConstatns.SUBCATEGORY_LAPTOP_BAGS));
		bagBagPacksLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_BAGS_ACCESSORIES,
				DealingMartConstatns.SUBCATEGORY_BAGPACKS));

		elecMobileAccessoriesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_ELECTRONICS,
				DealingMartConstatns.SUBCATEGORY_MOBILE_ACCESSORIES));
		elecTabletLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_ELECTRONICS,
				DealingMartConstatns.SUBCATEGORY_TABLET));

		bookSatBookLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_STATIONARY,
				DealingMartConstatns.SUBCATEGORY_BOOK));
		bookStaStationaryLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_STATIONARY,
				DealingMartConstatns.SUBCATEGORY_STATIONARY));

		gifPhotoFrameLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_PHOTO_FRAME));
		gifGoldenDivinityLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_GOLDEN_DIVINITY));
		gifCrystalShowpieceLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_CRYSTAL_SHOWPIECE));
		gifDesignerWatchLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_DESIGNER_WATCH));
		gifSunglassesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_SUNGLASSES));
		gifHandiCraftLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_HANDICRAFT));
		gifHipFlackLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_HIP_FLASK));
		gifIndianFlagLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_INDIAN_FLAG));
		gifLordGaneshaLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_LORD_GANESHA));
		gifSilverPenLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_SILVER_PEN));
		gifPaintingLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_GIFT_ARTICALS,
				DealingMartConstatns.SUBCATEGORY_PAINTING));

		menCravatLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_CRAVATS));
		menCuffLinksLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_CUFFLINKS));
		menSocksLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_SOCKS));
		menSuspenderLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_SUSPENDER));
		menTiesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_TIES));
		menShirtsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_SHIRTS));
		menTShirtsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_T_SHIRTS));
		menTuxedaStusLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_TUXEDA_STUDS));
		menShortsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_SHORTS));
		menTrackPantstLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_TRACKPANTS));
		menCapriLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_MEN,
				DealingMartConstatns.SUBCATEGORY_CAPRI));

		womenCapriLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_WCAPRI));
		womenJegginsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_JEGGINGS));
		womenNightwearLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_NIGHTWEAR));
		womenPendantLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_PENDANT));
		womenPyjamasLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_PYJAMAS));
		womenSlipLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_SLIP));
		womenTshirtsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_WOMEN_T_SHIRTS));
		womenTightsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_TIGHTS));
		womenNecklaceLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_NECKLACE));
		womenBanglesLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_BANGLE));
		womenBraceletLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_BRACELET));
		womenEarringsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_EARRINGS));
		womenHandBagsLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_WOMEN,
				DealingMartConstatns.SUBCATEGORY_HAND_BAGS));

		// helth and wllness
		helthKitLayout.setOnClickListener(new OnCategoryClick(
				DealingMartConstatns.CATEGORY_HEALTH_WELLNESS,
				DealingMartConstatns.SUBCATEGORY_KIT));

		lifeStyleTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int visibility = View.GONE;
				if (lifOtherAccessoriesLayout.getVisibility() == View.VISIBLE) {
					visibility = View.GONE;
					lifePlusTextView.setText("+");
				} else {
					visibility = View.VISIBLE;
					lifePlusTextView.setText("-");
				}

				lifClothingLayout.setVisibility(visibility);
				lifOtherAccessoriesLayout.setVisibility(visibility);
				clothingLineView.setVisibility(visibility);
				otherAccessoriesLineView.setVisibility(visibility);

			}
		});

		applianceTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int visiblity = View.GONE;
				if (appOfficeDecoreLayout.getVisibility() == View.VISIBLE) {
					visiblity = View.GONE;
					appliancePlusTextView.setText("+");
				} else {
					visiblity = View.VISIBLE;
					appliancePlusTextView.setText("-");
				}
				appOfficeDecoreLayout.setVisibility(visiblity);
				officeDecoreLineView.setVisibility(visiblity);

			}
		});

		bagsAccessoriesTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int visiblity = View.GONE;
				if (bagBagPacksLayout.getVisibility() == View.VISIBLE) {
					visiblity = View.GONE;
					bagsAccessoriesView.setText("+");
				} else {
					visiblity = View.VISIBLE;
					bagsAccessoriesView.setText("-");
				}
				bagBagPacksLayout.setVisibility(visiblity);
				backPacksLineView.setVisibility(visiblity);
			}
		});

		giftTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int visiblity = View.GONE;
				if (gifPaintingLayout.getVisibility() == View.VISIBLE) {
					visiblity = View.GONE;
					giftPlusTextView.setText("+");
				} else {
					visiblity = View.VISIBLE;
					giftPlusTextView.setText("-");
				}

				gifCrystalShowpieceLayout.setVisibility(visiblity);
				gifDesignerWatchLayout.setVisibility(visiblity);
				gifSunglassesLayout.setVisibility(visiblity);
				gifHandiCraftLayout.setVisibility(visiblity);
				gifHipFlackLayout.setVisibility(visiblity);
				gifIndianFlagLayout.setVisibility(visiblity);
				gifLordGaneshaLayout.setVisibility(visiblity);
				gifSilverPenLayout.setVisibility(visiblity);
				gifPaintingLayout.setVisibility(visiblity);

				goldenDivinityLineView.setVisibility(visiblity);
				crystalLineView.setVisibility(visiblity);
				designerLineView.setVisibility(visiblity);
				sunglassLineView.setVisibility(visiblity);
				handicraftLineView.setVisibility(visiblity);
				hipflackLineView.setVisibility(visiblity);
				indianflagLineView.setVisibility(visiblity);
				lordganeshaLineView.setVisibility(visiblity);
				silverpenLineView.setVisibility(visiblity);
				paintingLineView.setVisibility(visiblity);

			}
		});

		menTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int visiblity = View.GONE;
				if (menCapriLayout.getVisibility() == View.VISIBLE) {
					visiblity = View.GONE;
					menPlusTextView.setText("+");
				} else {
					visiblity = View.VISIBLE;
					menPlusTextView.setText("-");
				}

				menSocksLayout.setVisibility(visiblity);
				menSuspenderLayout.setVisibility(visiblity);
				menTiesLayout.setVisibility(visiblity);
				menShirtsLayout.setVisibility(visiblity);
				menTShirtsLayout.setVisibility(visiblity);
				menTuxedaStusLayout.setVisibility(visiblity);
				menShortsLayout.setVisibility(visiblity);
				menTrackPantstLayout.setVisibility(visiblity);
				menCapriLayout.setVisibility(visiblity);

				cufflinkLineView.setVisibility(visiblity);
				sockLineView.setVisibility(visiblity);
				suspenderLineView.setVisibility(visiblity);
				tiesLineView.setVisibility(visiblity);
				shirtsLineView.setVisibility(visiblity);
				tShirtsLineView.setVisibility(visiblity);
				tuxedaLineView.setVisibility(visiblity);
				shortLineView.setVisibility(visiblity);
				trackPantsLineView.setVisibility(visiblity);
				capriLineView.setVisibility(visiblity);

			}
		});

		womenTitleLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int visiblity = View.GONE;
				if (womenHandBagsLayout.getVisibility() == View.VISIBLE) {
					visiblity = View.GONE;
					womenPlusTextView.setText("+");
				} else {
					visiblity = View.VISIBLE;
					womenPlusTextView.setText("-");
				}

				womenNightwearLayout.setVisibility(visiblity);
				womenPendantLayout.setVisibility(visiblity);
				womenPyjamasLayout.setVisibility(visiblity);
				womenSlipLayout.setVisibility(visiblity);
				womenTshirtsLayout.setVisibility(visiblity);
				womenNecklaceLayout.setVisibility(visiblity);
				womenBanglesLayout.setVisibility(visiblity);
				womenBraceletLayout.setVisibility(visiblity);
				womenEarringsLayout.setVisibility(visiblity);
				womenHandBagsLayout.setVisibility(visiblity);
				womenTightsLayout.setVisibility(visiblity);

				nightWearLineView.setVisibility(visiblity);
				pendantLineView.setVisibility(visiblity);
				pyjamasLineView.setVisibility(visiblity);
				slipLineView.setVisibility(visiblity);
				wTShirtsLineView.setVisibility(visiblity);
				tightsLineView.setVisibility(visiblity);
				necklaceLineView.setVisibility(visiblity);
				bangleLineView.setVisibility(visiblity);
				braceletLineView.setVisibility(visiblity);
				earringsLineView.setVisibility(visiblity);
				handBagsLineView.setVisibility(visiblity);

			}
		});

	}

	private class OnCategoryClick implements OnClickListener {

		private String category;
		private String subCategory;

		OnCategoryClick(String category, String subCategory) {
			this.category = category;
			this.subCategory = subCategory;
		}

		@Override
		public void onClick(View v) {
			onCategorySelectedListener
					.onCategorySelected(category, subCategory);
		}

	}
}
