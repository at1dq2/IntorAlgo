package sort;

public class InsertionSort {

	
	
	public static void sort(long[] temp) {
		if(temp != null && temp.length > 1) {
			for(int i = 1; i <  temp.length; i++) {
				long key = temp[i];
				int j = i-1;
				//j--;
				while(((j) >= 0) && (temp[j]>key)) {
					temp[j+1] = temp[j];
					j--;
				}
				temp[++j] = key; 
			}
		}		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long[] arrays = {12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331};
		long start = System.nanoTime();
		InsertionSort.sort(arrays);
		long end = System.nanoTime();
		System.out.println("cost time is " + (end -start));
		
		for(long temp:arrays) {
			System.out.println(temp);
			System.out.println();
		}
		
	}

}
