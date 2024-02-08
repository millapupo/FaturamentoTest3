package org.faturamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Cria um ObjectMapper p ler o JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Lê o arquivo e mapeia para um array de objetos Java
            Dados[] dado = objectMapper.readValue(new File("dados.json"), Dados[].class);
            // Filtra os dados com valor != de zero
            Dados[] dadosComFaturamento = Arrays.stream(dado).filter(dados -> dados.getValor() > 0).toArray(Dados[]::new);
            // Calcula o < e o > valor
            double menorFaturamento = Arrays.stream(dadosComFaturamento)
                    .mapToDouble(Dados::getValor)
                    .min()
                    .orElse(0);
            double maiorFaturamento = Arrays.stream(dadosComFaturamento)
                    .mapToDouble(Dados::getValor)
                    .max()
                    .orElse(0);
            // Calcula a média mensal de faturamento
            double mediaMensal = Arrays.stream(dadosComFaturamento)
                    .mapToDouble(Dados::getValor)
                    .average()
                    .orElse(0);
            // Conta o número de dias com faturamento acima da média mensal
            long diasAcimaDaMedia = Arrays.stream(dadosComFaturamento)
                    .filter(dados -> dados.getValor() > mediaMensal)
                    .count();

            // Printa os resultados
            System.out.println("Menor valor de faturamento: " + menorFaturamento);
            System.out.println("Maior valor de faturamento: " + maiorFaturamento);
            System.out.println("Número de dias com faturamento acima da média mensal: " + diasAcimaDaMedia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}