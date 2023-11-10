package br.com.hiokdev.financialtransactions.domain.entity;

public enum TransactionType {
  DEBITO(1),
  BOLETO(2),
  FINANCIAMENTO(3),
  CREDITO(4),
  RECEBIMENTO_EMPRESTIMO(5),
  VENDAS(6),
  RECEBIMENTO_TED(7),
  RECEBIMENTO_DOC(8),
  ALUGUEL(9);

  private int type;

  private TransactionType(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }

  public int getSignal() {
    return switch(type) {
      case 1, 4, 5, 6, 7, 8 -> 1;
      case  2, 3, 9 -> -1;
      default -> 0;
    };
  }

  public static TransactionType findByType(int type) {
    for(TransactionType transactionType: values()) {
      if (transactionType.type == type) {
        return transactionType;
      }
    }
    throw new IllegalArgumentException("Invalid type: " + type);
  }

}
