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

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

public class CategoryServlet extends BaseBackServlet{

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		// 传引用 params 对象被修改
		String name = params.get("name");
		Category category = new Category();
		category.setName(name);
		categoryDAO.add(category);
		
		// 获取文件夹绝对路径 = 项目的工作目录 + "img/category"
		File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
		// 把 imageFolder 作为目录创建文件对象
		File file = new File(imageFolder, category.getId() + ".jpg");
		file.getParentFile().mkdirs();
		
		try {
			// available() 提前得知数据流中有多少个字节可以读取
			if (null != is && 0 != is.available()) {
				try (FileOutputStream fos = new FileOutputStream(file);) {
					byte[] b = new byte[1024 * 1024];
					int length = 0;
					// read(byte[] b) 从输入流中读取一定数量的字节,并将其存储在缓冲区数组 b 中,以整数形式返回实际读取的字节数
					while (-1 != (length = is.read(b))) {
						// 将 length 个字节的数据,从数组 b 的 0 位置开始写出到输出流
						fos.write(b, 0, length);
					}
					// 强制把缓存中的数据写入硬盘,无论缓存是否已满
					fos.flush();
					
					// 把文件保存为 JPG 格式
					BufferedImage img = ImageUtil.chang2jpg(file);
					ImageIO.write(img, "jpg", file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		categoryDAO.delete(id);
		return "@admin_category_list";
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(id);
		request.setAttribute("category", category);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		String name = params.get("name");
		int id = Integer.parseInt(params.get("id"));
		
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		
		categoryDAO.update(category);
		
		File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
		File file = new File(imageFolder, category.getId() + ".jpg");
		// 如果父目录不存在就创建父目录 存在则不起作用
		file.getParentFile().mkdirs();
		
		try {
			if (null != is && 0 != is.available()) {
				try (FileOutputStream fos = new FileOutputStream(file);) {
					byte[] b = new byte[1024 * 1024];
					int length = 0;
					// is.read(b) 从输入流中读取一定数量的字节存在缓冲区 b 中,并以整数形式返回实际读取的字节数
					while (-1 != (length = is.read(b))) {
						fos.write(b, 0, length);
					}
					fos.flush();
					// 通过如下代码把文件保存为 jpg 格式
					BufferedImage img = ImageUtil.chang2jpg(file);
					ImageIO.write(img, "jpg", file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "@admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
		
		int total = categoryDAO.getTotal();
		page.setTotal(total);
		
		request.setAttribute("thecs", cs);
		request.setAttribute("page", page);
		
		return "admin/listCategory.jsp";
	}
	
}
