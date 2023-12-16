package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;


@Controller
public class ItemController {
	
	@Autowired // seq.1
	ItemRepository itemRepository;
	
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
	public String items() {
		// 画面遷移
		return "items";
	}
	
}
