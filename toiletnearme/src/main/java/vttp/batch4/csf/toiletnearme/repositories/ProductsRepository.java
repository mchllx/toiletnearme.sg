package vttp.batch4.csf.toiletnearme.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.models.Toilet;

@Repository
public class ProductsRepository {

  @Autowired
  private MongoTemplate template;

  /*
   * Get all product categories
   * db.products.distinct('Category', { Category: { $nin: [ '', null ] } })
   */
  public List<String> getProductCategories() {
    Query query = Query.query(
      Criteria.where("Category").nin("", null)
    );
    return template.findDistinct(query, "Category", Utils.MONGODB_1, String.class);
  }

  /*
   * db.products.find(
   *   { Category: { $regex: category, $options: 'i' }, }
   * )
   * .limit(count)
   * .sort( { DiscountPrice: -1 } )
   */
  public List<Toilet> getProductByCategory(String category, int count) {
    Query query = Query.query(Criteria.where("Category").regex(category, "i"))
      .with(Sort.by(Sort.Direction.DESC, "DiscountPrice"))
      .limit(count);

    return template.find(query, Document.class, Utils.MONGODB_1)
        .stream()
        .map(Utils::toToilet)
        .toList();
  }

}

