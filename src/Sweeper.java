import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Sweeper {
	private static final String SweeperArg = "sweeper";
	private static final String PlaceArg = "place";
	private static final String DummySweeper = "dummy_sweeper";
	private static final String DummyPlace = "dummy_place";
	
	private static final String[] SweeperArray = {
			"A",
			"B",
			"C",
			"D",
			"E",
			"F",
			"G",
	};
	private static final String[] PlaceArray = {
			"a",
			"b",
			"c",
			"d",
			"e",
			"f",
			"g",
	};

	public static void main(String[] args) {
		Sweeper sweeper = new Sweeper(args);
		List<SweeperPlaceCombination> sweeperPlaceCombinationList = sweeper.getSweeperPlaceCombinations(null);
		
		if(sweeperPlaceCombinationList != null){
			for(SweeperPlaceCombination sweeperPlaceCombination : sweeperPlaceCombinationList){
				System.out.println(sweeperPlaceCombination.sweeper + ": " + sweeperPlaceCombination.place);
			}
		}
	}
	
	
	private ArrayList<String> mSweeperList = new ArrayList<String>();
	private ArrayList<String> mPlaceList = new ArrayList<String>();

	public Sweeper(String[] args){
		if(args != null){
			for(int i=0, sizeI=args.length; i<sizeI; i++){
				String[] keyValueArray = args[i].split("=");
				if(keyValueArray != null && keyValueArray.length > 1){
					ArrayList<String> tempList = null;

					if(SweeperArg.equals(keyValueArray[0])){
						tempList = mSweeperList;
					}else if(PlaceArg.equals(keyValueArray[0])){
						tempList = mPlaceList;
					}

					if(tempList != null){
						tempList.clear();
						String[] valueArray = keyValueArray[1].split(",");
						if(valueArray != null && valueArray.length > 0){
							for(int j=0, sizeJ=valueArray.length; j<sizeJ; j++){
								tempList.add(valueArray[j]);
							}
						}
					}
				}
			}
		}
		
		if(mSweeperList.size() == 0){
			for(int i=0, sizeI=SweeperArray.length; i<sizeI; i++){
				mSweeperList.add(SweeperArray[i]);
			}
		}

		if(mPlaceList.size() == 0){
			for(int i=0, sizeI=PlaceArray.length; i<sizeI; i++){
				mPlaceList.add(PlaceArray[i]);
			}
		}
		
		int sweeperCount = mSweeperList.size();
		int placeCount = mPlaceList.size();
		
		if(sweeperCount != placeCount){
			if(sweeperCount > placeCount){
				// mPlaceListにダミーの場所（それの担当になった人は休み）を不足分追加
				for(int i=placeCount; i<sweeperCount; i++){
					mPlaceList.add(DummyPlace);
				}
			}else{
				// mSweepListにダミーの人（その人が担当になった場所は当日掃除しない）を不足分追加
				for(int i=placeCount; i<sweeperCount; i++){
					mSweeperList.add(DummySweeper);					
				}
			}
		}
	}

	public List<SweeperPlaceCombination> getSweeperPlaceCombinations(Calendar currentCalendar){
		List<SweeperPlaceCombination> ret = new ArrayList<SweeperPlaceCombination>();
		currentCalendar = currentCalendar == null ? Calendar.getInstance(Locale.JAPAN) : currentCalendar;
		
		int dayOfYear = currentCalendar.get(Calendar.DAY_OF_YEAR);
		int weekOfYear = currentCalendar.get(Calendar.WEEK_OF_YEAR) - 1;
		
		for(int i=0, sizeI=mSweeperList.size(); i<sizeI; i++){
			SweeperPlaceCombination combination = new SweeperPlaceCombination();
			combination.sweeper = mSweeperList.get(i);
			combination.place = mPlaceList.get((i + dayOfYear - (2 * weekOfYear)) % sizeI);
			ret.add(combination);
		}
		
		return ret;
	}
	
	public static class SweeperPlaceCombination {
		public String sweeper;
		public String place;
	}
}
