package sort;

public class MergeSort {

	public static void merge(long[] temp, int p, int q, int r) {
		int n1 = q - p + 1;
		int n2 = r - q;
		long[] lTemp = new long[n1];
		long[] rTemp = new long[n2];
		//copy data
		int i = 0;
		for(; i < n1; i++) {
			lTemp[i] = temp[p+i];
		}
		int j = 0;
		for(; j < n2; j++) {
			rTemp[j] = temp[q+1+j];
		}
		i = 0;
		j = 0;
		//merge
		for(int k = p; k <= r; k++) {
			if(i == n1) {
				//lTemp reach its end. then copy all rTemp to temp
				temp[k] = rTemp[j];
				j++;
				continue;
			}
			if(j == n2) {
				//rTemp reach its end. then copy all lTemp to temp
				temp[k] = lTemp[i];
				i++;
				continue;
			}
			
			if(lTemp[i] < rTemp[j]) {
				temp[k] = lTemp[i];
				i++;
			}
			else if (lTemp[i] >= rTemp[j]){
				temp[k] = rTemp[j];
				j++;
			}
		}
	}
	
	public static void sort(long[] temp, int p, int r) {
		if(temp != null && temp.length > 1) {
			if(r <= p) {
				return;
			}
			int q = (r+p) / 2;
			MergeSort.sort(temp, p, q);
			MergeSort.sort(temp, q + 1, r);
			MergeSort.merge(temp, p, q, r);
		}
	}
	
	public static void main(String[] args) {
		long[] arrays = {12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331,12, 32, 33, 33, 42, 11, 1, 22, 12, 321, 3331};
		long start = System.nanoTime();
		MergeSort.sort(arrays, 0, 6);
		long end = System.nanoTime();
		System.out.println("cost time is " + (end -start));
		for(long temp:arrays) {
			System.out.println(temp);
			System.out.println();
		}
		
	}
	
}
