package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductAllComparator implements Comparator<Product> {

	@Override
	public int compare(Product p1, Product p2) {
		// µπ≈≈–Ú
		return p2.getSaleCount() * p2.getReviewCount() -
				p1.getSaleCount() * p1.getReviewCount();
	}
	
}
