<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mon panier</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background: #15803D; color: white; }
        .total { font-size: 1.2em; font-weight: bold; margin-top: 15px; }
    </style>
</head>
<body>
    <h1>Mon panier</h1>

    <c:choose>
        <c:when test="${empty lignes}">
            <p>Votre panier est vide.</p>
        </c:when>
        <c:otherwise>
            <c:set var="total" value="0"/>
            <table>
                <tr>
                    <th>Produit</th><th>Prix unitaire</th><th>Quantité</th>
                    <th>Sous-total</th><th>Action</th>
                </tr>
                <c:forEach var="l" items="${lignes}">
                    <tr>
                        <td><c:out value="${l.produit.designation}"/></td>
                        <td>${l.produit.prix} DH</td>
                        <td>${l.quantite}</td>
                        <td>${l.sousTotal} DH</td>
                        <td><a href="panier?action=supprimer&idLigne=${l.id}">Supprimer</a></td>
                    </tr>
                    <c:set var="total" value="${total + l.sousTotal}"/>
                </c:forEach>
            </table>
            <p class="total">Total : ${total} DH</p>
        </c:otherwise>
    </c:choose>

    <p><a href="vitrine?action=lister">← Retour à la vitrine</a></p>
</body>
</html>