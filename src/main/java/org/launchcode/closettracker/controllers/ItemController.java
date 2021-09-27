package org.launchcode.closettracker.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.launchcode.closettracker.models.Color;
import org.launchcode.closettracker.models.Item;
import org.launchcode.closettracker.models.Season;
import org.launchcode.closettracker.models.dto.UserDTO;
import org.launchcode.closettracker.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("items")
public class ItemController {

    private static final String userSessionKey = "user";
    private static final String itemSessionKey = "item";

    @Autowired
    private ItemRepository itemRepository;

    // CREATE ITEM: Show form
    @GetMapping("create-item")
    public String displayCreateItemForm(Model model) {
            model.addAttribute(new Item());
            model.addAttribute("title", "Create User Account");
            return "items/create-item";
    }

    // CREATE ITEM: Process form
    @PostMapping("create-item")
    public String processCreateItemForm(@ModelAttribute @Valid Item newItem,
                                         Errors errors, Model model,@RequestParam("image") MultipartFile multipartFile,HttpSession session) throws IOException {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            return "items/create-item";
        }

        if(newItem.getItemName().isEmpty()) {
            model.addAttribute("itemNameError", "Item name is required");
            return "items/create-item";
        }

        if(newItem.getType().isEmpty()) {
            model.addAttribute("itemTypeError", "Item type is required");
            return "items/create-item";
        }

        if(newItem.getColor().toString() == "SELECT") {
            model.addAttribute("itemError", "Select a valid color");
            return "items/create-item";
        }

        if(newItem.getSize().isEmpty()) {
            model.addAttribute("itemSizeError", "Item size is required");
            return "items/create-item";
        }

        if(newItem.getSeason().toString() == "SELECT") {
            model.addAttribute("itemSeasonError", "Select a valid season");
            return "items/create-item";
        }

        if(multipartFile.getBytes().length <=0) {
            model.addAttribute("itemImageError", "Select a item image");
            return "items/create-item";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        byte[] image1 = multipartFile.getBytes();
        newItem.setItemImage(multipartFile.getBytes());

        Integer userId = (Integer) session.getAttribute(userSessionKey);
        newItem.setUser_Id(userId);

        itemRepository.save(newItem);
        return "redirect:";
    }

  /*  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "update-item")
    public String processUpdateItemForm(@ModelAttribute Item newItem,
                                        Errors errors, Model model,@RequestParam("image") MultipartFile multipartFile,HttpSession session) throws IOException {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Item");
            return "items/edit-item";
        }

        Optional<Item> result = itemRepository.findById(newItem.getId());

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: ");
        } else {
            Item item = result.get();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            byte[] image1 = multipartFile.getBytes();
            item.setItemImage(multipartFile.getBytes());

            Integer userId = (Integer) session.getAttribute(userSessionKey);
            item.setUser_Id(userId);
            item.setItemName(newItem.getItemName());
            item.setSize(newItem.getSize());

            itemRepository.save(item);
        }


        return "redirect:";
    }*/




    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "update-item")
    public String processUpdateItemForm(@ModelAttribute Item newItem,
                                        Errors errors, Model model,@RequestParam("image") MultipartFile multipartFile,HttpSession session) throws IOException {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Update Item");
            return "items/edit-item";
        }
        Integer itemId = (Integer) session.getAttribute(itemSessionKey);
        Optional<Item> result = itemRepository.findById(itemId);
        Item editItem = result.get();



        if(newItem.getItemName().isEmpty()) {
            model.addAttribute("itemNameError", "Item name is required");
            editItem.setItemName("");
            editItem.setType(newItem.getType());
            editItem.setColor(newItem.getColor());
            editItem.setSize(newItem.getSize());
            editItem.setSeason(newItem.getSeason());
            model.addAttribute("item",editItem);
            model.addAttribute("item",editItem);
            return "items/edit-item";
        }

        if(newItem.getType().isEmpty()) {
            model.addAttribute("itemTypeError", "Item type is required");
            editItem.setType("");
            editItem.setItemName(newItem.getItemName());
            editItem.setColor(newItem.getColor());
            editItem.setSize(newItem.getSize());
            editItem.setSeason(newItem.getSeason());
            model.addAttribute("item",editItem);
            return "items/edit-item";
        }

        if(newItem.getColor().toString() == "SELECT") {
            model.addAttribute("itemError", "Select a valid color");
            editItem.setColor(Color.SELECT);
            editItem.setType(newItem.getType());
            editItem.setItemName(newItem.getItemName());
            editItem.setColor(newItem.getColor());
            editItem.setSize(newItem.getSize());
            editItem.setSeason(newItem.getSeason());
            model.addAttribute("item",editItem);
            return "items/edit-item";
        }

        if(newItem.getSize().isEmpty()) {
            model.addAttribute("itemSizeError", "Item size is required");
            editItem.setSize("");
            editItem.setColor(newItem.getColor());
            editItem.setType(newItem.getType());
            editItem.setItemName(newItem.getItemName());
            editItem.setColor(newItem.getColor());
            editItem.setSeason(newItem.getSeason());
            model.addAttribute("item",editItem);
            return "items/edit-item";
        }

        if(newItem.getSeason().toString() == "SELECT") {
            model.addAttribute("itemSeasonError", "Select a valid season");
            editItem.setSeason(Season.SELECT);
            editItem.setColor(newItem.getColor());
            editItem.setType(newItem.getType());
            editItem.setItemName(newItem.getItemName());
            editItem.setColor(newItem.getColor());
            editItem.setSize(newItem.getSize());
            editItem.setSeason(newItem.getSeason());
            model.addAttribute("item",editItem);
            return "items/edit-item";
        }

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: ");
        } else {
            Item item = result.get();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            byte[] image1 = multipartFile.getBytes();
            if(multipartFile.getBytes().length !=0) {
                item.setItemImage(multipartFile.getBytes());
            }

            Integer userId = (Integer) session.getAttribute(userSessionKey);
            item.setUser_Id(userId);
            item.setItemName(newItem.getItemName());
            item.setSize(newItem.getSize());
            item.setType(newItem.getType());
            item.setColor(newItem.getColor());
            item.setSeason(newItem.getSeason());

            itemRepository.save(item);
        }


        return "redirect:";
    }

    // DELETE ITEM(s): Show form
   /*@ @GetMapping("delete")
    public String displayDeleteItemForm(Model model) {
        model.addAttribute("title", "Delete Items");
        model.addAttribute("items", itemRepository.findAll());
        return "items/delete";
    }

    // DELETE ITEM(s): Process form
    PostMapping("delete")
    public String processDeleteItemsForm(@RequestParam(required = false) int[] itemIds) {

        if (itemIds != null) {
            for (int id : itemIds) {
                itemRepository.deleteById(id);
            }
        }

        return "redirect:";
    }*/

    @GetMapping("delete")
    public String processDeleteItemsForm(@RequestParam Integer itemId) {
        itemRepository.deleteById(itemId);
        return "redirect:";
    }

    @GetMapping("edit")
    public String displayEditItemDetails(@RequestParam Integer itemId, Model model,HttpSession session) {

        Optional<Item> result = itemRepository.findById(itemId);

        session.setAttribute(itemSessionKey, itemId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: " + itemId);
        } else {
            Item item = result.get();
            model.addAttribute("title", item.getItemName() + " Details");
            model.addAttribute("itemId",itemId);
            model.addAttribute("item", item);
        }

        return "items/edit-item";
    }

    @GetMapping("viewItem")
    public String viewItem(@RequestParam Integer itemId, Model model,HttpSession session) {

        Optional<Item> result = itemRepository.findById(itemId);

        session.setAttribute(itemSessionKey, itemId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: " + itemId);
        } else {
            Item item = result.get();
            model.addAttribute("title", item.getItemName() + " Details");
            model.addAttribute("itemId",itemId);
            model.addAttribute("item", item);
        }

        return "items/view-item";
    }


    // We are making View Item Details and Edit Item Details the same page
    @GetMapping("detail")
    public String displayItemDetails(@RequestParam Integer itemId, Model model) {

        Optional<Item> result = itemRepository.findById(itemId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: " + itemId);
        } else {
            Item item = result.get();
            model.addAttribute("title", item.getItemName() + " Details");
            model.addAttribute("item", item);
        }

        return "items/detail";
    }

    @PostMapping("detail")
    public String updateItemDetails(@RequestParam Integer itemId, Model model) {

        Optional<Item> result = itemRepository.findById(itemId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Item ID: " + itemId);
        } else {
            Item item = result.get();
            model.addAttribute("title", item.getItemName() + " Details");
            model.addAttribute("item", item);
            itemRepository.save(item);
        }
        return "items/detail";
    }

    @GetMapping
    public String displayAllItems(Model objModel,HttpSession session)
    {
        //objModel.addAttribute("items", itemRepository.findAll());

        Integer user_Id = (Integer) session.getAttribute(userSessionKey);
        List<Item> result =(List<Item>) itemRepository.findAll();

        List<Item> result1  = result.stream().filter(c -> c.getUser_Id() == user_Id).collect(Collectors.toList());

        if(result1.size() > 0) {
            objModel.addAttribute("items", result1);
        }
        else
        {
            objModel.addAttribute("itemSearch", "Your closet is empty! Please add items..");
        }


        //objModel.addAttribute("items", itemRepository.findItemByUserId(user_Id));

        return "items/closet";
    }

    @PostMapping("search")
    public String searchItem(@RequestParam String itemName,Model objModel,HttpSession session)
    {
        //objModel.addAttribute("items", itemRepository.findAll());

        Integer user_Id = (Integer) session.getAttribute(userSessionKey);
        List<Item> result =(List<Item>) itemRepository.findAll();

        List<Item> result1  = result.stream().filter(c -> c.getUser_Id() == user_Id && c.getItemName().contains(itemName)).collect(Collectors.toList());

        if(result1.size() > 0) {
            objModel.addAttribute("items", result1);
        }
        else
        {
            objModel.addAttribute("itemSearch", "No items found for '" + itemName + "'");
        }


        //objModel.addAttribute("items", itemRepository.findItemByUserId(user_Id));

        return "items/closet";
    }

    @GetMapping("/display/image/{id}")
    @ResponseBody
    public void showProductImage ( @PathVariable("id") int id,
                                   HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg"); // Or whatever format we want to use

        Optional<Item> imageGallery = itemRepository.findById(id);

        InputStream is = new ByteArrayInputStream(imageGallery.get().getItemImage());
        IOUtils.copy(is, response.getOutputStream());

        //Files.write(Paths.get("resources/image/" + imageGallery.get().getName() + "." + "jpg"), imageGallery.get().getPic());

    }

}
