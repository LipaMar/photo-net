export class Comparator {

  public static NUMBER_COMPARATOR = (a: any, b: any) => a > b ? 1 : a === b ? 0 : -1
  public static STRING_COMPARATOR = (a: string, b: string) => a.localeCompare(b);

}
