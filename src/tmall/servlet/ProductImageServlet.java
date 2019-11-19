package tmall.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.ProductImageDAO;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class ProductImageServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		int pid = Integer.parseInt(params.get("pid"));
		String type = params.get("type");
		Product product = productDAO.get(pid);
		
		ProductImage productImage = new ProductImage();
		productImage.setType(type);
		productImage.setProduct(product);
		productImageDAO.add(productImage);

		String fileName = productImage.getId() + ".jpg";
		String imageFolder = null;
		String imageFolder_Small = null;
		String imageFolder_Middle = null;
		
		if (ProductImageDAO.type_single.equals(productImage.getType())) {
			imageFolder = request.getServletContext().getRealPath("img/productSingle");
			imageFolder_Small = request.getServletContext().getRealPath("img/productSingle_Small");
			imageFolder_Middle = request.getServletContext().getRealPath("img/productSingle_Middle");
		} else {
			imageFolder = request.getServletContext().getRealPath("img/productDetail");
		}

		File file = new File(imageFolder, fileName);
		file.getParentFile().mkdirs();
		
		try {
			if (null != is && 0 != is.available()) {
				try (FileOutputStream fos = new FileOutputStream(file);) {
					byte[] b = new byte[1024 * 1024];
					int length = 0;
					while (-1 != (length = is.read(b))) {
						fos.write(b, 0, length);
					}
					fos.flush();
					
					BufferedImage img = ImageUtil.chang2jpg(file);
					ImageIO.write(img, "jpg", file);
					
					if (ProductImageDAO.type_single.equals(productImage.getType())) {
						File file_Small = new File(imageFolder_Small, fileName);
						File file_Middle = new File(imageFolder_Middle, fileName);
						
						file_Small.getParentFile().mkdirs();
						file_Middle.getParentFile().mkdirs();
						
						ImageUtil.resizeImage(file, 56, 56, file_Small);
						ImageUtil.resizeImage(file, 217, 190, file_Middle);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_productImage_list?pid=" + pid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		ProductImage productImage = productImageDAO.get(id);
		productImageDAO.delete(id);
		
		if (ProductImageDAO.type_single.equals(productImage.getType())) {
			String imageFolder_Single = request.getServletContext().getRealPath("img/productSingle");
			String imageFolder_Small = request.getServletContext().getRealPath("img/productSingle_Small");
			String imageFolder_Middle = request.getServletContext().getRealPath("img/productSingle_Middle");
			
			File file_Single = new File(imageFolder_Single, productImage.getId() + ".jpg");
			File file_Small = new File(imageFolder_Small ,productImage.getId() + ".jpg");
			File file_Middle = new File(imageFolder_Middle ,productImage.getId() + ".jpg");
			
			file_Single.delete();
			file_Small.delete();
			file_Middle.delete();
		} else {
			String imageFolder_Detail = request.getServletContext().getRealPath("img/productDetail");
			File file_Detail = new File(imageFolder_Detail, productImage.getId() + ".jpg");
			file_Detail.delete();
		}
		
		return "@admin_productImage_list?pid=" + productImage.getProduct().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		List<ProductImage> pisSingle = productImageDAO.list(product, ProductImageDAO.type_single);
		List<ProductImage> pisDetail = productImageDAO.list(product, ProductImageDAO.type_detail);
		
		request.setAttribute("product", product);
		request.setAttribute("pisSingle", pisSingle);
		request.setAttribute("pisDetail", pisDetail);
		
		return "admin/listProductImage.jsp";
	}

}
