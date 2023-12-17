package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;




@Controller
public class ItemController {
	
	@Autowired // seq.1
	ItemRepository itemRepository;
	@Autowired // seq.2
	CategoryRepository categoryRepository;
	
	// 商品一覧表示
	@GetMapping("/")
	public String index(Model model) {
		// すべての商品の商品リストを取得 seq.2
		List<Item> itemList = itemRepository.findAll();
		// 取得した商品リストをスコープに登録 seq.3
		model.addAttribute("itemList", itemList);
		// 画面遷移 seq.4
		return "items";
	}
	
	// カテゴリーによる絞り込み
	@GetMapping("/items")
	public String items(
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			Model model) {
		// すべてのカテゴリーのカテゴリーリストを取得 seq.3
		List<Category> categoryList = categoryRepository.findAll();
		// 取得したカテゴリーリストをスコープに登録 seq.4
		model.addAttribute("categoryList", categoryList);
		// リクエストパラメータによる商品リスト取得の分岐
		List<Item> itemList = null;
		if (categoryId == null) {
			// カテゴリーIDが未送信の場合 seq.5-1
			itemList = itemRepository.findAll();
		} else {
			// カテゴリーIDが送信されている場合 seq.5-2
			itemList = itemRepository.findByCategoryId(categoryId);
		}
		// 取得した商品リストをスコープに登録 seq.6
		model.addAttribute("itemList", itemList);
		// 画面遷移 seq.7
		return "items";
	}
	
	// 新規登録画面表示
	@GetMapping("/items/add")
	public String create() {
		// 画面遷移 seq.1
		return "addItem";
	}
	
	// 新規登録処理
	@PostMapping("/items/add")
	public String store(
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "") Integer price) {
		// リクエストパラメータをもとに登録する商品をインスタンス化 seq.2
		Item item = new Item(categoryId, name, price);
		// 生成した商品インスタンスを永続化 seq.3
		itemRepository.save(item);
		// 画面遷移 seq.4
		return "redirect:/items";
	}
	
	// 更新画面表示
	@GetMapping("/items/{id}/edit")
	public String edit(
			@PathVariable("id") Integer id,
			Model model) {
		// 更新対象商品をデータベースから取得 seq.2
		Item item = itemRepository.findById(id).get();
		// 取得した商品をスコープに登録 seq.3
		model.addAttribute("item", item);
		// 画面遷移 seq.4
		return "editItem";
	}
	
	// 更新処理
	@PostMapping("/items/{id}/edit")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "") Integer price) {
		// パスパラメータとリクエストパラメータをもとに更新対象商品をインスタンス化 seq.2
		Item item = new Item(id, categoryId, name, price);
		// インスタンス化した商品を永続化 seq.3
		itemRepository.save(item);
		// 画面遷移 seq.4
		return "redirect:/items";
	}
	
}
