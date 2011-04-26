package sort;

public class CaculateKForInsertAndMerge {
	public static long getK() {
		long k = 1;
		long startK = -1;
		long endK = -1;
		for(;;k++) {
			//construct array from biggest to smallest 
			long[] temp = new long[(int) k];
			long value = k;
			int j = 0;
			while(value > 0) {
				temp[j] = value;
				value--;
				j++;
			}
			
			long[] temp2 = new long[temp.length];
			for(int i = 0; i < temp.length; i++) {
				temp2[i] = temp[i];
			}
			long insertStart = System.nanoTime();
			InsertionSort.sort(temp);
			long insertEnd = System.nanoTime();
			long insertCostTime = insertEnd -insertStart;
			
			
			
			long mergeStart = System.nanoTime();
			MergeSort.sort(temp2, 0, (temp2.length - 1));
			long mergeEnd = System.nanoTime();
			long mergeCostTime = mergeEnd -mergeStart;
			if(startK == -1 && mergeCostTime > insertCostTime) {
				startK = k;
			}
			else if(startK != -1 && mergeCostTime <= insertCostTime){
				endK = k;
				System.out.println("startK is " + startK);
				System.out.println("endK is " + endK);
				break;
			}
		}
		return endK;
	}
	public static void main(String[] args) {
		CaculateKForInsertAndMerge.getK();
	}
}
