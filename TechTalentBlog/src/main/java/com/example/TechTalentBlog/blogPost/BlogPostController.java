package com.example.TechTalentBlog.blogPost;

import com.example.TechTalentBlog.repo.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;
    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping(value="/")
    public String index(BlogPost blogPost, Model model) {
        model.addAttribute("posts", posts);
        return "blogpost/HomePage";
    }
//@GetMapping(value = "/")
//public void index(BlogPost blogPost, Model model) {
//    posts.removeAll(posts);
//    for (BlogPost post : blogPostRepository.findAll()) {
//        posts.add(post);
//    }
//}

    private BlogPost blogPost;

    @PostMapping(value = "/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(new BlogPost(blogPost.getTitle(), blogPost.getAuthor(), blogPost.getBlogEntry()));
//        posts = blogPostRepository.listAll();
        posts.add(blogPost);
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

    @GetMapping(value = "/blogposts/new")
    public String newBlog(BlogPost blogPost){
        return "blogpost/CreatePost";
    }

    @RequestMapping(value="/blogposts/{id}", method= RequestMethod.DELETE)
//    @PostMapping(value="/blogposts/{id}")
    public String deletePostWithId(@PathVariable Long id, BlogPost blogPost){
        blogPostRepository.deleteById(id);
        return "blogpost/HomePage";
    }

    @PostMapping(value= "/blogpost/edit/{id}")
    public String editPostWithId(@PathVariable("id") Long id, BlogPost blogPost, Model model) {

        Optional<BlogPost> post = blogPostRepository.findById(id);
        if(post.isPresent())  {
            model.addAttribute(post);

        }else {
            System.out.printf("Could not find post at %sn", id);
        }

        return "blogpost/edit";
    }

    @PostMapping("/blogpost/edit")
    public String updateBlogPost(Long id, BlogPost blog, Model model) {
        System.out.println(id);
        System.out.println(blog.getTitle());
        System.out.println(blog.getAuthor());
        System.out.println(blog.getBlogEntry());

        BlogPost update = blogPostRepository.findById(id).orElse(null);
        if(update !=null) {
            update.setAuthor(blog.getAuthor());
            update.setAuthor(blog.getTitle());
            update.setAuthor(blog.getBlogEntry());
            blogPostRepository.save(update);
        } else {
            System.out.printf("Could not find post at %sn", id);
        }

        assert update != null;
        model.addAttribute("title", update.getTitle());
        model.addAttribute("author", update.getAuthor());
        model.addAttribute("blogEntry", update.getBlogEntry());
        model.addAttribute("id", update.getId());

        return "blogpost/result";
    }

//    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
////    @GetMapping(value = "/blogposts/{id}")
//    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
//        Optional<BlogPost> post = blogPostRepository.findById(id);
//        if (post.isPresent()) {
//            BlogPost actualPost = post.get();
//            model.addAttribute("blogPost", actualPost);
//        }
//        return "blogpost/Edit";
//    }
//
//    @PostMapping(value = "/blogposts/update/{id}")
//    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
//        Optional<BlogPost> post = blogPostRepository.findById(id);
//        if (post.isPresent()) {
//            BlogPost actualPost = post.get();
//            actualPost.setTitle(blogPost.getTitle());
//            actualPost.setAuthor(blogPost.getAuthor());
//            actualPost.setBlogEntry(blogPost.getBlogEntry());
//            blogPostRepository.save(actualPost);
//            model.addAttribute("blogPost", actualPost);
//        }
//
//        return "blogpost/result";
//    }


}
